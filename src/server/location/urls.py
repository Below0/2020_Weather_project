from django.urls import path

from location import views

urlpatterns = [
    path('', views.location_list, name='location_list'),
    path('find/', views.find_code, name='find_location')
]