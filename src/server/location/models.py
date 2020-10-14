from django.db import models

# Create your models here.
from django.utils import timezone


class Location(models.Model):
    name = models.CharField(max_length=10)
    location_id = models.CharField(max_length=  8)

    def __str__(self):
        return self.name

    def get_id(self):
        return self.location_id
