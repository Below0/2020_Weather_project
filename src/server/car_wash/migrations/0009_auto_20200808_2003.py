# Generated by Django 3.0.7 on 2020-08-08 11:03

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_wash', '0008_auto_20200808_1937'),
    ]

    operations = [
        migrations.AlterField(
            model_name='washer',
            name='address',
            field=models.CharField(default='-', max_length=20),
        ),
        migrations.AlterField(
            model_name='washer',
            name='district',
            field=models.CharField(default='강남구', max_length=10),
        ),
        migrations.AlterField(
            model_name='washer',
            name='dong',
            field=models.CharField(default='도곡동', max_length=10),
        ),
        migrations.AlterField(
            model_name='washer',
            name='phone',
            field=models.CharField(default='010-0000-0000', max_length=20),
        ),
    ]
