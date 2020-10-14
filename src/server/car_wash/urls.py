from django.urls import path

from car_wash import views

urlpatterns = [
    path('list/', views.washer_list, name='washer_list'),
    path('detail/<int:pk>', views.washer_detail, name='washer_detail'),
    path('review/<int:pk>', views.review, name='write_review'),
]
