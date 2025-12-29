import os
from dotenv import load_dotenv

load_dotenv()

from .base import *

DEBUG = False

SECRET_KEY = os.environ.get('SECRET_KEY', 'fallback-key-change-in-production')

ALLOWED_HOSTS = os.environ.get('ALLOWED_HOSTS', 'localhost').split(',')

# Production database configuration for Supabase PostgreSQL
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': os.environ.get('DB_NAME'),
        'USER': os.environ.get('DB_USER'),
        'PASSWORD': os.environ.get('DB_PASSWORD'),
        'HOST': os.environ.get('DB_HOST'),
        'PORT': os.environ.get('DB_PORT'),
        'OPTIONS': {
            'sslmode': os.environ.get('DB_SSL_MODE', 'require'),
        },
    }
}
