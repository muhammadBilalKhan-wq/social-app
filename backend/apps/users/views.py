from rest_framework import generics, status, permissions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.views import APIView
from django.contrib.auth import authenticate
from rest_framework_simplejwt.tokens import RefreshToken
from .models import User
from .serializers import RegisterSerializer, LoginSerializer, UserSerializer




@api_view(['POST'])
def register_view(request):
    serializer = RegisterSerializer(data=request.data)

    if serializer.is_valid():
        user = serializer.save()
        refresh = RefreshToken.for_user(user)
        return Response({
            "success": True,
            "user_id": user.id,
            "access": str(refresh.access_token),
            "refresh": str(refresh)
        }, status=status.HTTP_201_CREATED)

    # Get the first error message
    errors = serializer.errors
    if errors:
        first_error_key = next(iter(errors))
        first_error = errors[first_error_key][0] if errors[first_error_key] else "Registration failed"

        return Response({
            "success": False,
            "error": str(first_error)
        }, status=status.HTTP_400_BAD_REQUEST)

    return Response({
        "success": False,
        "error": "Registration failed"
    }, status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def login_view(request):
    # Accept either email or phone
    identifier = request.data.get('email') or request.data.get('phone')
    password = request.data.get('password')

    if not identifier or not password:
        return Response({
            "success": False,
            "error": "Email/Phone and password are required"
        }, status=status.HTTP_400_BAD_REQUEST)

    identifier = identifier.strip()
    password = password.strip()

    # Try to find user by email or phone
    try:
        # Check if identifier looks like an email
        if '@' in identifier:
            user_obj = User.objects.get(email__iexact=identifier)
        else:
            # Assume it's a phone number
            user_obj = User.objects.get(phone=identifier)
    except User.DoesNotExist:
        return Response({
            "success": False,
            "error": "Account does not exist"
        }, status=status.HTTP_404_NOT_FOUND)

    if not user_obj.is_active:
        return Response({
            "success": False,
            "error": "Account is deactivated"
        }, status=status.HTTP_401_UNAUTHORIZED)

    if user_obj.check_password(password):
        user = user_obj
        refresh = RefreshToken.for_user(user)
        return Response({
            "success": True,
            "user_id": user.id,
            "access": str(refresh.access_token),
            "refresh": str(refresh)
        }, status=status.HTTP_200_OK)
    else:
        return Response({
            "success": False,
            "error": "Incorrect password"
        }, status=status.HTTP_401_UNAUTHORIZED)


@api_view(['GET'])
@permission_classes([permissions.IsAuthenticated])
def protected_view(request):
    return Response({
        "message": "You are authenticated!",
        "user": request.user.username
    })


@api_view(['POST'])
def check_email(request):
    email = request.data.get('email')
    if not email:
        return Response({"available": False}, status=status.HTTP_400_BAD_REQUEST)
    available = not User.objects.filter(email__iexact=email.strip()).exists()
    return Response({"available": available})


@api_view(['POST'])
def check_phone(request):
    phone = request.data.get('phone')
    if not phone:
        return Response({"available": False}, status=status.HTTP_400_BAD_REQUEST)
    available = not User.objects.filter(phone=phone.strip()).exists()
    return Response({"available": available})


@api_view(['GET'])
@permission_classes([permissions.IsAuthenticated])
def get_me(request):
    serializer = UserSerializer(request.user)
    return Response({
        "success": True,
        "user": serializer.data
    })
