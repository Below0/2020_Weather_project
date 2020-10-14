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
            return 2

        temp = int(value)
        return 5-int(temp/20)

    def make_rnlv(self):

        for i in range(self.cnt):
            am_lv = self.calc_rn(self.rec[i].get('rnStAm', None))
            pm_lv = self.calc_rn(self.rec[i].get('rnStPm', None))
            self.lv_list.append((am_lv, pm_lv))

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

        self.pm10 = result

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
            
            
            #doc['score'] = score
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

class MidWeatherService:  # 중기예보 서비스 모듈
    # default = 서울

    def __init__(self):
        self.fc_cache = {}
        self.TIME_OUT = 0.5

        with open('/cron/location_code.csv', 'r') as f:
            rdr = csv.reader(f)
            self.code = [item for item in rdr][0]


    def make_tmfc(self):  # 현재 시간을 기점으로 가장 마지막 예보 시간을 리턴
        tmfc = time.strftime('%Y%m%d', time.localtime(time.time()))
        hour = int(time.strftime('%H', time.localtime(time.time())))

        if hour >= 18:
            tmfc += "1800"
        elif hour >= 6:
            tmfc += "0600"
        else:
            tmfc = (datetime.now() - timedelta(days=1)).strftime('%Y%m%d')+"1800"

        return "&tmFc=" + tmfc

    def make_fcstID(self, regID):

        reg = regID[2]

        if reg == 'F':
            if regID[3] == '1':
                fcstID = '11F10000'
            else:
                fcstID = '11F20000'
        elif reg == 'B':
            fcstID = '11B00000'
        else:
            fcstID = regID[0:4] + '0000'

        return fcstID

    def call_api(self, address):
        req = None
        try_cnt = 0

        while try_cnt < 10 and req is None:
            try:
                req = urllib.request.urlopen(address, timeout=3)
            except Exception as err:
                try_cnt += 1
                print("TIMEOUT Retry:", try_cnt)
                time.sleep(self.TIME_OUT)

        return req

    def get_temperature(self, regID='11B10101'):
        address = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=" + service_key + "&numOfRows=10&pageNo=1&dataType=JSON"
        req = self.call_api(address + self.make_tmfc() + "&regId=" + regID)
        res = req.readline()

        j = json.loads(res)
        if j['response']['header']['resultCode'] == '00':
            return j['response']['body']['items']['item'][0]

        else:
            print('API CALL Failure')

    def get_fcst(self, regID='11B00000'):
        cache_hit = self.fc_cache.get(regID, None)
        if cache_hit is not None:
            return cache_hit

        address = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=" + service_key + "&numOfRows=10&pageNo=1&dataType=JSON"

        req = self.call_api(address + self.make_tmfc() + "&regId=" + regID)

        res = req.readline()
        j = json.loads(res)

        if j['response']['header']['resultCode'] == '00':
            content = j['response']['body']['items']['item'][0]
            self.fc_cache[content['regId']] = content
            return content

        else:
            print('API CALL Failure')

    def make_record(self, regID='11B10101'):
        date = datetime.now()
        result = []
        ta = self.get_temperature(regID)
        fcst = self.get_fcst(self.make_fcstID(regID))

        for day in range(3, 11):
            target_date = (date + timedelta(days=day)).strftime('%Y%m%d')
            record = {'date': target_date, 'regID': regID, 'taMin': ta['taMin' + str(day)],
                      'taMax': ta['taMax' + str(day)]}

            rn_key = 'rnSt' + str(day)
            wf_key = 'wf' + str(day)

            if day < 8:
                record['rnStAm'] = fcst[rn_key + 'Am']
                record['rnStPm'] = fcst[rn_key + 'Pm']
                record['wfAm'] = fcst[wf_key + 'Am']
                record['wfPm'] = fcst[wf_key + 'Pm']
            else:
                record['rnStAm'] = fcst[rn_key]
                record['rnStPm'] = fcst[rn_key]
                record['wfAm'] = fcst[wf_key]
                record['wfPm'] = fcst[wf_key]

            result.append(record)
        return result

    def run(self):
        lst = []
        size = len(self.code)
        print("Update mid Weather Start[0/{}]".format(size))
        for i in range(0, size):
            if i % 17 == 0:
                print('Update.... {}/{}'.format(i, size))
            rec = self.make_record(self.code[i])
            lst.extend(rec)

        bulk_list = [pymongo.UpdateOne({'date': x['date'], 'regID': x['regID']}, {'$set': x}, upsert=True) for x in
                     lst]
        weather_col.bulk_write(bulk_list)

        print("Complete to update mid Weather")

m_service = MidWeatherService()
m_service.run()

calculator = ScoreCaculator()
calculator.run()
