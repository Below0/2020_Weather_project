from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render, redirect
from django.core import serializers
from django.db.models import Avg

from .forms import PostForm
from .models import *
from accounts.models import User
# Create your views here.


def washer_list(request):

    if request.method == "GET":
        
        if len(request.GET.keys()) == 0:
            washer_set = Washer.objects.all()
        else:
            time_filter = {}
            for k, v in request.GET.items():
                if k.startswith('open'):
                    time_filter[k] = v

            filter_dict = {
                    key:value
                    for key, value in request.GET.items() \
                            if key.startswith('open') is False
                    }

            washer_set = Washer.objects.filter(**filter_dict)


            delete_list = []
            if len(time_filter) > 0 :
                for w in washer_set:
                    if w.time_check(time_filter) is False:
                        delete_list.append(w.id)

                if len(delete_list) > 0:
                    washer_set = washer_set.exclude(id__in=delete_list)
                    
        
        data = list(washer_set.values())
        for i in range(len(data)):
            temp = Washer.objects.get(pk=data[i]['id'])
            wash_list = []
            for item in temp.wash_type.all().values():
                wash_list.append(item['name'])
            data[i]['wash'] = wash_list
        return JsonResponse(data, safe=False, json_dumps_params={'ensure_ascii': False})

    elif request.method == "POST":
        form = PostForm(request.POST)
        if form.is_valid():  # 폼 검증 메소드
            washer = form.save(commit=False)  # lotto 오브젝트를 form으로 부터 가져오지만, 실제로 DB반영은 하지 않는다.
            washer.save()
            return HttpResponse(washer.name)  # url의 name을 경로대신 입력한다.
        else:
            return HttpResponse('error')

def washer_detail(request, pk):
    washer = Washer.objects.get(id=pk)
    wash_type = list(washer.wash_type.all().values())
    reviews = washer.reviews.all()
    score = reviews.aggregate(Avg('score'))['score__avg']
    
    if score is None:
        score = 0
    
    content = {
            "name": washer.name,
            "type": wash_type,
            "score": round(score,1)
            }
    return JsonResponse(content, safe=False, json_dumps_params={'ensure_ascii': False})

def review(request, pk):
    washer = Washer.objects.get(id = pk)
    if request.method == "GET":
        reviews = list(washer.reviews.all().values())
        review_list = []
        for r in reviews:
            user = User.objects.get(id=r['user_id'])
            r['user_name'] = user.name
            review_list.append(r)
        data = {}
        data['id'] = pk
        data['reviews'] = reviews
        return JsonResponse(data, safe=False, json_dumps_params={'ensure_ascii': False})

    if request.method == "POST":
        washer = Washer.objects.get(id = pk)
        user_id = request.POST['id']
        user = User.objects.get(id = user_id)
        content = request.POST['content']
        score = request.POST['score']
        review = Review.objects.create(washer = washer, user = user, content = content, score = score)
        return JsonResponse({"status":"200", "id":review.id}, safe=False, json_dumps_params={'ensure_ascii': False})
