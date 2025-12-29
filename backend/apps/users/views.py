from rest_framework import generics, status, permissions
from rest_framework.response import Response
from rest_framework.decorators import api_view, permission_classes
from rest_framework.views import APIView
from django.contrib.auth import authenticate
from rest_framework_simplejwt.tokens import RefreshToken
from .models import User
from .serializers import RegisterSerializer, LoginSerializer, UserSerializer

# for testing 
# import json
# from rest_framework.decorators import api_view
# from rest_framework.response import Response


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
    if 'email' in serializer.errors:
        return Response({
            "success": False,
            "error": "Invalid email format"
        }, status=status.HTTP_400_BAD_REQUEST)
    return Response({
        "success": False,
        "error": str(serializer.errors)
    }, status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def login_view(request):
    print("Request data:", request.data)
    serializer = LoginSerializer(data=request.data)
    if serializer.is_valid():
        email = serializer.validated_data['email'].strip()
        password = serializer.validated_data['password'].strip()
        print(f"Email: '{email}', Password length: {len(password)}")
        try:
            user_obj = User.objects.get(email__iexact=email)
            print(f"User found: {user_obj.email}")
        except User.DoesNotExist:
            print("User not found")
            return Response({
                "success": False,
                "error": "Account does not exist"
            }, status=status.HTTP_404_NOT_FOUND)
        if not user_obj.is_active:
            print("User not active")
            return Response({
                "success": False,
                "error": "Account is deactivated"
            }, status=status.HTTP_401_UNAUTHORIZED)
        if user_obj.check_password(password):
            print("Password correct")
            user = user_obj
        else:
            print("Password incorrect")
            return Response({
                "success": False,
                "error": "Incorrect password"
            }, status=status.HTTP_401_UNAUTHORIZED)
        refresh = RefreshToken.for_user(user)
        return Response({
            "success": True,
            "user_id": user.id,
            "access": str(refresh.access_token),
            "refresh": str(refresh)
        }, status=status.HTTP_200_OK)
    print("Serializer errors:", serializer.errors)
    if 'email' in serializer.errors:
        return Response({
            "success": False,
            "error": "Invalid email format"
        }, status=status.HTTP_400_BAD_REQUEST)
    return Response({
        "success": False,
        "error": str(serializer.errors)
    }, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET'])
@permission_classes([permissions.IsAuthenticated])
def protected_view(request):
    return Response({
        "message": "You are authenticated!",
        "user": request.user.username
    })


@api_view(['GET'])
@permission_classes([permissions.IsAuthenticated])
def get_me(request):
    serializer = UserSerializer(request.user)
    return Response({
        "success": True,
        "user": serializer.data
    })
