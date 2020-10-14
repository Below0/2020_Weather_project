import datetime

from django.http import JsonResponse
from .mongoManager import MongoDbManager
# Create your views here.


def score_list(request, region):
    date = datetime.datetime.now().strftime("%Y%m%d")
    temp = MongoDbManager().get_sorted_score({"regID": region, "date": {"$gte": date}}, "score")

    if temp.count() < 1:
        return JsonResponse({"message": "해당하는 데이터가 없습니다."}, json_dumps_params= {'ensure_ascii': False})

    result = [item for item in temp]

    for i in range(len(result)):
        del result[i]['_id']

    return JsonResponse({"message": "OK",
                         "contents": result
                         }, json_dumps_params= {'ensure_ascii': False})


def score_detail(request, region, date):
    temp = MongoDbManager().get_score_from_collection({"regID": region, "date": date})
    if temp.count() < 1:
       return JsonResponse({"message": "해당하는 데이터가 없습니다."}, json_dumps_params={'ensure_ascii': False})

    result = [item for item in temp]
    for i in range(len(result)):
        del result[i]['_id']

    return JsonResponse({"message": "OK",
                         "contents": result[0]
                         }, json_dumps_params={'ensure_ascii': False})
