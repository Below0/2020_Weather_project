# Generated by Django 3.0.7 on 2020-08-08 10:21

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('car_wash', '0005_auto_20200808_1804'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='washer',
            name='phone',
        ),
    ]