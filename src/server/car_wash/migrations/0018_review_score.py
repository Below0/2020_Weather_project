# Generated by Django 3.0.7 on 2020-09-06 15:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_wash', '0017_auto_20200906_1459'),
    ]

    operations = [
        migrations.AddField(
            model_name='review',
            name='score',
            field=models.SmallIntegerField(default=3),
        ),
    ]