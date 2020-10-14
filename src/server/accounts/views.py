import os
import requests
from django.http import JsonResponse, HttpResponse

from django.shortcuts import render, redirect
from .models import *

# Create your views here.

def index(request):
    return render(request, 'login/index.html')

def user_check(request):
    if request.method != "POST":
        return HttpResponse("Unvalid request")

    user_id = request.POST['id']
    user_name = request.POST['name']

    response = {}

    user, created = User.objects.get_or_create(id=user_id, name = user_name)

    if created:
        response['status'] = 'register'
    else:
        response['status'] = 'is exist'

    response['id'] = user_id
    response['user_name'] = user_name


    return JsonResponse(response)
