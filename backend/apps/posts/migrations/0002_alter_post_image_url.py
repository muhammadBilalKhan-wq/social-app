# Generated migration for changing image_url from URLField to TextField

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('posts', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='post',
            name='image_url',
            field=models.TextField(blank=True, null=True),
        ),
    ]
