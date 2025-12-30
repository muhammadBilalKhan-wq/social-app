import os
from dotenv import load_dotenv
from .base import *

load_dotenv()

DEBUG = True

SECRET_KEY = 'django-insecure-dev-key-change-in-production'

ALLOWED_HOSTS = ['*']  # Allow all hosts for local development

# Use SQLite for development
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': BASE_DIR / 'db.sqlite3',
    }
}
