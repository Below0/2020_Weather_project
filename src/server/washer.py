import pymysql
import json
import requests; from urllib.parse import urlparse


scsc_db = pymysql.connect(
    user='scsc',
    passwd='scsc',
    host='127.0.0.1',
    db='scsc',
    charset='utf8'
)

kakao_key = "9a3bb4a52c09371ab3dcab7b20d03210"
base_url = "https://dapi.kakao.com/v2/local/search/address.json?page=1&size=10&query="

null_time = "99:99-99:99"

def call_api(address):
    req = None
    try_cnt = 0

    while try_cnt < 10 and req is None:
        try:
            req = requests.get(urlparse(base_url+address).geturl(), headers={"Authorization":"KakaoAK "+kakao_key})
        except Exception as err:
            try_cnt += 1
            print("TIMEOUT Retry:", try_cnt)
            time.sleep(self.TIME_OUT)

    #print(req.json())
    res = req.json()["documents"][0]["address"]
    
    return res


cursor = scsc_db.cursor(pymysql.cursors.DictCursor)

def format_check(time):
    if time is None or len(time) < 11:
        return False

    if time[2] == ':' and time[2] == time[8] and time[5] == '-':
        return True
    else:
        return False

def find_time(words):
    for word in words:
        if format_check(word):
            if len(word) > 11:
                word = word[:11]
            return word
    return None

def time_parser(input_time):
    days = ["open_week","open_sat","open_sun"]
    keywords = {"평일":[0],"매일":[0,1,2],"토":[1],"일":[2],"주말":[1,2]}
    time_list = input_time.split('/')
    res = {}

    for schedule in time_list:
        schedule = schedule.strip()
        words = schedule.split(' ')
        if not words[0] in keywords.keys():
            continue

        time = find_time(words[1:])
        if time is None:
            continue

        input_ids = keywords[words[0]]
        
        for did in input_ids:
            res[days[did]] = time

    return res



def parse_input(target):
    wash_dict = {'자동세차':'1', '손세차':'2', '디테일링':'3', '출장세차':'4', '실내세차':'5', '출장세차(손세차)':'4',
            '셀프세차':'6','스팀세차':'7'}
    name, address, phone, wash_type, open_time = target.split('	')
   
    addr_info = call_api(address)
   
    lat = addr_info["y"]
    lon = addr_info["x"]

    #print(addr_info)
    city = addr_info["region_1depth_name"]+"시"
    district = addr_info["region_2depth_name"]
    dong = addr_info["region_3depth_h_name"]

    #city = "서울시"
    #district = "성동구"
    #dong = "성수동"

    wash_list = wash_type.split('/')
    time = time_parser(open_time)
    
    open_week = time.get("open_week", null_time)
    open_sat = time.get("open_sat", null_time)
    open_sun = time.get("open_sun", null_time)
    
    sql = '''INSERT INTO `car_wash_washer` (`name`, `lat`, `lon`, `address`, `phone`, `city`, `district`, `dong`,
    `open_sat`,`open_sun`,`open_week`)
    VALUES ("{}","{}","{}","{}","{}","{}","{}","{}","{}","{}","{}");'''.format(name,lat,lon,address,phone,city,district,dong, open_sat, open_sun,open_week)
    print(sql)
    
    cursor.execute(sql)
    scsc_db.commit()
    wid = cursor.lastrowid

    for item in wash_list:
        wash_id = wash_dict.get(item)
        sql = '''INSERT INTO `car_wash_washer_wash_type` (washer_id, washtype_id)
        VALUES ("{}","{}");'''.format(wid, wash_id)
        cursor.execute(sql)
        print(sql)
    scsc_db.commit()


inp = input('세차장 정보 입력: ')
parse_input(inp)
print('입력 완료')
