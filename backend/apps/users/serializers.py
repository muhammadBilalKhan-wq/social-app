from rest_framework import serializers
from django.contrib.auth.password_validation import validate_password
from django.core.exceptions import ValidationError
from django.core.validators import EmailValidator
import re
from .models import User


def validate_strong_password(password):
    """
    Validate password strength: 8-32 chars, uppercase, lowercase, number, special char.
    """
    errors = []

    if len(password) < 8:
        errors.append("Password must be at least 8 characters long.")
    elif len(password) > 32:
        errors.append("Password must be at most 32 characters long.")

    if not re.search(r'[A-Z]', password):
        errors.append("Password must contain at least one uppercase letter.")

    if not re.search(r'[a-z]', password):
        errors.append("Password must contain at least one lowercase letter.")

    if not re.search(r'\d', password):
        errors.append("Password must contain at least one number.")

    if not re.search(r'[!@#$%^&*]', password):
        errors.append("Password must contain at least one special character (!@#$%^&*).")

    if errors:
        raise ValidationError(errors)


class RegisterSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True, required=True, validators=[validate_strong_password])
    email = serializers.EmailField(validators=[EmailValidator()], required=False)
    phone = serializers.CharField(max_length=20, required=False)

    class Meta:
        model = User
        fields = ('email', 'phone', 'password', 'name')

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

    def validate(self, data):
        # Ensure either email or phone is provided, but not both
        email = data.get('email')
        phone = data.get('phone')

        if not email and not phone:
            raise serializers.ValidationError("Either email or phone must be provided.")

        if email and phone:
            raise serializers.ValidationError("Cannot provide both email and phone.")

        # Validate phone number format and length
        if phone:
            # Check basic phone number format (digits only after normalization)
            normalized_phone = self._normalize_phone(phone)
            if not normalized_phone.isdigit():
                raise serializers.ValidationError("Phone number must contain only digits.")

            # Check reasonable length (7-12 digits for international numbers)
            if len(normalized_phone) < 7:
                raise serializers.ValidationError("Phone number is too short.")
            if len(normalized_phone) > 12:
                raise serializers.ValidationError("Phone number is too long.")

            # Check for existing user
            existing_users = User.objects.exclude(phone__isnull=True).exclude(phone='')
            for user in existing_users:
                if self._normalize_phone(user.phone) == normalized_phone:
                    raise serializers.ValidationError("Account with this phone number already exists.")

        # Check for existing user by email
        if email and User.objects.filter(email__iexact=email).exists():
            raise serializers.ValidationError("Account with this email already exists.")

        return data

    def create(self, validated_data):
        user = User.objects.create_user(
            email=validated_data.get('email'),
            phone=validated_data.get('phone'),
            password=validated_data['password'],
            name=validated_data['name']
        )
        return user


class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField(required=True)
    password = serializers.CharField(required=True)


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('id', 'email', 'phone', 'name')
