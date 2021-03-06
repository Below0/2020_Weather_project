# Generated by Django 3.0.7 on 2020-09-02 12:22

from django.db import migrations, models
import django.db.models.deletion
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        ('accounts', '__first__'),
        ('car_wash', '0013_auto_20200829_1731'),
    ]

    operations = [
        migrations.CreateModel(
            name='Review',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_date', models.DateTimeField(default=django.utils.timezone.now)),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='accounts.User')),
                ('washer', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='car_wash.Washer')),
            ],
        ),
    ]
