from rest_framework import serializers
from django.contrib.auth.password_validation import validate_password
from django.core.validators import EmailValidator
from .models import User


class RegisterSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True, required=True, min_length=6)
    email = serializers.EmailField(validators=[EmailValidator()], required=False)
    phone = serializers.CharField(max_length=20, required=False)

    class Meta:
        model = User
        fields = ('email', 'phone', 'password', 'name')

    def validate(self, data):
        # Ensure either email or phone is provided, but not both
        email = data.get('email')
        phone = data.get('phone')

        if not email and not phone:
            raise serializers.ValidationError("Either email or phone must be provided.")

        if email and phone:
            raise serializers.ValidationError("Cannot provide both email and phone.")

        # Check for existing user
        if email and User.objects.filter(email__iexact=email).exists():
            raise serializers.ValidationError("Account with this email already exists.")

        if phone and User.objects.filter(phone=phone).exists():
            raise serializers.ValidationError("Account with this phone number already exists.")

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
