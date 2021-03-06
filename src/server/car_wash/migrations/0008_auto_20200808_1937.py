# Generated by Django 3.0.7 on 2020-08-08 10:37

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_wash', '0007_washer_phone'),
    ]

    operations = [
        migrations.AddField(
            model_name='washer',
            name='city',
            field=models.CharField(default='서울시', max_length=5),
        ),
        migrations.AddField(
            model_name='washer',
            name='district',
            field=models.CharField(default='강남구', max_length=5),
        ),
        migrations.AddField(
            model_name='washer',
            name='dong',
            field=models.CharField(default='도곡동', max_length=7),
        ),
        migrations.AlterField(
            model_name='washer',
            name='address',
            field=models.CharField(default='-', max_length=15),
        ),
    ]
