from django.db import models
from accounts.models import User
# Create your models here.
from django.utils import timezone
import time

class WashType(models.Model):
    tid = models.AutoField(primary_key=True)
    name = models.CharField(max_length=10)

    def __str__(self):
        return self.name

class Washer(models.Model):
    name = models.CharField(max_length=20)
    lat = models.FloatField()
    lon = models.FloatField()
    address = models.CharField(max_length=50, default='-')
    city = models.CharField(max_length=7, default='서울시')
    district = models.CharField(max_length=10, default='강남구')
    dong = models.CharField(max_length=10, default='도곡동')
    phone = models.CharField(max_length=20, default = '010-0000-0000')
    wash_type = models.ManyToManyField(WashType)
    open_week = models.CharField(max_length=20, default = '09:00-18:00')
    open_sat = models.CharField(max_length=20, default = '09:00-18:00')
    open_sun = models.CharField(max_length=20, default = '99:99-99:99')

    def __str__(self):
        return self.name

    def time_check(self, tf):
        def time_parse(t):
            op, cl = t.split('-')

            if op == "99:99":
                op = "00:00"
                cl = "00:00"

            op = time.strptime(op, '%H:%M')
            
            if cl == "24:00":
                cl = "23:59"

            cl = time.strptime(cl, '%H:%M')
            return op, cl


        for k, v in tf.items():
            v = time.strptime(v, '%H')
            if k == "open_week":
                op,cl = time_parse(self.open_week)
            
            elif k == "open_sat":
                op,cl = time_parse(self.open_sat)
            
            elif k == "open_sun":
                op,cl = time_parse(self.open_sun)
            
            if v < op or v >= cl :
                return False

        return True
                

                

class Review(models.Model):
    washer = models.ForeignKey('Washer', 
            on_delete=models.CASCADE,
            related_name='reviews'
            )
    user = models.ForeignKey(User, on_delete=models.CASCADE,
            )
    score = models.SmallIntegerField(default=3)
    created_date = models.DateTimeField(default=timezone.now)
    content = models.CharField(max_length=500, default = '내용이 없습니다.')

    def __str__(self):
        return self.user

