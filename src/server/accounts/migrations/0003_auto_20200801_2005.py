# Generated by Django 3.0.7 on 2020-08-01 11:05

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '0002_auto_20200801_1701'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='user',
            name='account_id',
        ),
        migrations.RemoveField(
            model_name='user',
            name='account_pw',
        ),
    ]
