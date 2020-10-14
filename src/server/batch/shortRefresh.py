import csv

import pymongo
import json
import urllib.request
import time
from datetime import datetime, timedelta

conn = pymongo.MongoClient('127.0.0.1', 27017)
db = conn.get_database('scsc')
weather_col = db.get_collection('weather')
score_col = db.get_collection('score')

with open("/cron/config/key.json", "r") as sk_json:
    service_key = json.load(sk_json)['key']


class ScoreCaculator:

    def __init__(self):
        self.lv_list = []
        self.rec = {}
        self.cnt = 0
        with open('/cron/location_code.csv', 'r') as f:
            rdr = csv.reader(f)
            self.code = [item for item in rdr][0]

    def calc_ta(self, i):
        line = self.rec[i]
        ta_min = int(line.get('taMin', 10))
        ta_max = int(line.get('taMax', 25))

        if ta_min < -10 or ta_max > 35:
            ta_level = 1
        elif ta_min < 0 or ta_max > 33:
            ta_level = 2
        elif ta_min < 5 or ta_max > 28:
            ta_level = 3
        elif ta_min < 10:
            ta_level = 4
        else:
            ta_level = 5
        return ta_level

    def calc_rn(self, value):
        if value is None:
            return 3

        temp = int(value)
        return 5-int(temp/20)

    def call_api(self, address):
        req = None
        try_cnt = 0

        while try_cnt < 10 and req is None:
            try:
                req = urllib.request.urlopen(address, timeout=10)
            except Exception as err:
                try_cnt += 1
                print("TIMEOUT Retry:", try_cnt)
                time.sleep(self.TIME_OUT)

        return req

    def call_air(self):
        air_address = "https://api.waqi.info/feed/seoul/?token=bfefdf2135b7497e3133bfe335a6db21842ad697"
        req = self.call_api(air_address)
        res = req.readline()
        j = json.loads(res)["data"]["forecast"]["daily"]["pm10"]
        result = {}
        for item in j:
            date_id = datetime.strptime(item['day'],"%Y-%m-%d").date().strftime('%Y%m%d')
            if item['avg'] < 31:
                pm10_lv = 4
            elif item['avg'] < 81:
                pm10_lv = 3
            elif item['avg'] < 150:
                pm10_lv = 2
            else:
                pm10_lv = 1

            result[date_id] = pm10_lv
        print("complete to load air data")
        self.pm10 = result


    def make_rnlv(self):

        for i in range(self.cnt):
            am_lv = self.calc_rn(self.rec[i].get('rnStAm', None))
            pm_lv = self.calc_rn(self.rec[i].get('rnStPm', None))
            self.lv_list.append((am_lv, pm_lv))

    def make_record(self):
        result = []
        self.make_rnlv()

        for i in range(0, self.cnt-3):
            line = self.rec[i]
            score = 0
            doc = {
                'date': line['date'],
                'regID': line['regID'],
                'pm10_lv': self.pm10[line['date']]
            }

            for j in range(i, i+4):
                pivot = min(self.lv_list[j])
                score += pivot*(i+5-j)
            #score += self.calc_ta(i)
            
           # doc['rn_score'] = score
            doc['rn_lv'] = int(score*(1/6))
            doc['ta_lv'] = self.calc_ta(i)
            result.append(doc)
        return result

    def run(self):
        size = len(self.code)
        res = []
        self.call_air()
        print("Start to refresh weather score...")
        for i in range(size):
            self.rec = weather_col.find({"regID": self.code[i], "date": {"$gte": datetime.now().strftime('%Y%m%d')}})
            self.cnt = self.rec.count()
            res.extend(self.make_record())
        bulk_list = [pymongo.UpdateOne({'date': x['date'], 'regID': x['regID']}, {'$set': x}, upsert=True) for x in res]
        score_col.bulk_write(bulk_list)
        print("Complete to update weather score")


class ShortWeatherService:

    def __init__(self):
        with open('/cron/location_code.csv', 'r') as f:
            rdr = csv.reader(f)
            self.code = [item for item in rdr][0]
        self.TIME_OUT = 0.5

    def call_api(self, address):
        req = None
        try_cnt = 0

        while try_cnt < 10 and req is None:
            try:
                req = urllib.request.urlopen(address, timeout=10)
            except Exception as err:
                try_cnt += 1
                print("TIMEOUT Retry:", try_cnt)
                time.sleep(self.TIME_OUT)

        return req

    def make_record(self, regID='11B10101'):
        result = []
        date = datetime.now()
        address = "http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst?serviceKey=" + service_key + "&numOfRows=10&pageNo=1&numOfRows=10&pageNo=1&dataType=JSON"

        req = self.call_api(address + "&regId=" + regID)
        res = req.readline()

        j = json.loads(res)

        if j['response']['header']['resultCode'] != '00':
            print('API CALL Failure')
            return

        j = j['response']['body']['items']['item']
        announce_time = str(j[0]['announceTime'])[-4:]

        if announce_time == "0500":
            for numEf in range(0, 6):
                if numEf % 2 == 0:
                    target_date = (date + timedelta(days=(numEf+1) / 2)).strftime('%Y%m%d')
                    record = {'date': target_date, 'regID': regID, 'rnStAm': j[numEf]['rnSt'], 'wfAm': j[numEf]['wf']}
                    if j[numEf]['ta'] is not None:
                        record['taMin'] = j[numEf]['ta']
                else:
                    record['rnStPm'] = j[numEf]['rnSt']
                    record['wfPm'] = j[numEf]['wf']
                    record['taMax'] = j[numEf]['ta']
                    result.append(record)

        else:
            record = {
                'date': date.strftime('%Y%m%d'),
                'regID': regID,
                'wfPm': j[0]['wf'],
                'rnStPm': j[0]['rnSt']
            }
            if j[0]['ta'] is not None:
                record['taMax'] = j[0]['ta']

            result.append(record)

            for numEf in range(1, 5):
                if numEf % 2 == 1:
                    target_date = (date + timedelta(days=(numEf+1) / 2)).strftime('%Y%m%d')
                    record = {'date': target_date, 'regID': regID, 'rnStAm': j[numEf]['rnSt'], 'wfAm': j[numEf]['wf'],
                              'taMin': j[numEf]['ta']}
                else:
                    record['rnStPm'] = j[numEf]['rnSt']
                    record['wfPm'] = j[numEf]['wf']
                    record['taMax'] = j[numEf]['ta']
                    result.append(record)

        return result

    def run(self):
        size = len(self.code)
        res = []
        print("Update short Weather Start[0/{}]".format(size))
        for i in range(0, size):
            if i % 10 == 0:
                print('Update.... {}/{}'.format(i, size))
            res.extend(self.make_record(self.code[i]))

        bulk_list = [pymongo.UpdateOne({'date': x['date'], 'regID': x['regID']}, {'$set': x}, upsert=True) for x in res]
        weather_col.bulk_write(bulk_list)
        print("Complete to update short Weather")


s_service = ShortWeatherService()
s_service.run()

calculator = ScoreCaculator()
calculator.run()
