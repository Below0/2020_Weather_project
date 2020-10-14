from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render
from django.core import serializers
from .models import Location
# Create your views here.


def location_list(request):
    locate_set = Location.objects.all()
    data = list(locate_set.values())
    return JsonResponse(data, safe=False, json_dumps_params={'ensure_ascii': False})


def find_code(request):
    city = request.GET.get('city', None)
    if city is None:
        return JsonResponse({"status":"fail"})

    code = Location.objects.get(name=city)
    return JsonResponse(
        {
            "code": code.get_id()
         },
        safe=False, json_dumps_params={'ensure_ascii': False}
    )