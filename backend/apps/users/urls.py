from django.urls import path
from .views import register_view, login_view, protected_view, get_me, check_email, check_phone
from rest_framework_simplejwt.views import TokenRefreshView, TokenBlacklistView

urlpatterns = [
    path('register/', register_view, name='register'),
    path('login/', login_view, name='login'),
    path('refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('logout/', TokenBlacklistView.as_view(), name='token_blacklist'),
    path('check_email/', check_email, name='check_email'),
    path('check_phone/', check_phone, name='check_phone'),
    path('me/', get_me, name='get_me'),
    path('protected/', protected_view, name='protected'),
]
