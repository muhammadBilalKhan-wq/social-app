from django.db import models
from django.contrib.auth.models import AbstractUser, BaseUserManager


class UserManager(BaseUserManager):
    def _normalize_phone(self, phone):
        """Normalize phone number by removing country codes, leading zeros, and non-digits."""
        if phone:
            # Remove country codes and non-digit characters
            import re
            # Remove +92, +1, etc. and any non-digit characters except the core number
            phone = re.sub(r'^(\+92|92|\+1|1)?', '', phone)
            # Remove any remaining non-digit characters
            phone = re.sub(r'\D', '', phone)
            # Remove leading zeros
            phone = phone.lstrip('0')
            return phone
        return phone

    def create_user(self, email=None, phone=None, password=None, name=None, **extra_fields):
        if not email and not phone:
            raise ValueError('Either Email or Phone must be set')
        if email:
            email = self.normalize_email(email)
            username = email
        elif phone:
            phone = self._normalize_phone(phone)
            username = phone
        else:
            raise ValueError('Invalid user creation parameters')

        user = self.model(email=email, phone=phone, username=username, name=name, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, password=None, name=None, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)

        if extra_fields.get('is_staff') is not True:
            raise ValueError('Superuser must have is_staff=True.')
        if extra_fields.get('is_superuser') is not True:
            raise ValueError('Superuser must have is_superuser=True.')

        return self.create_user(email, password, name, **extra_fields)


class User(AbstractUser):
    email = models.EmailField(unique=True, blank=True, null=True)
    phone = models.CharField(max_length=20, unique=True, blank=True, null=True)
    name = models.CharField(max_length=255)

    objects = UserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['name']

    def __str__(self):
        return self.email
