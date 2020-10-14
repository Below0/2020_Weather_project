from django.urls import path

from accounts import views

urlpatterns = [
    path('', views.index, name='index'),
    path('check', views.user_check, name="user_check"),
]
