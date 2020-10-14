import requests
from django.conf import settings
from django.db import models


class User(models.Model):
    name = models.CharField(
            max_length=20,
            default='name'
            )
    id = models.IntegerField(primary_key=True)

    def __str__(self):
        return self.name

