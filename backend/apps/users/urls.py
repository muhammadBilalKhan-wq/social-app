from django.urls import path
from .views import register_view, login_view, protected_view, get_me, check_email, check_phone

urlpatterns = [
    path('register/', register_view, name='register'),
    path('login/', login_view, name='login'),
    path('check_email/', check_email, name='check_email'),
    path('check_phone/', check_phone, name='check_phone'),
    path('me/', get_me, name='get_me'),
    path('protected/', protected_view, name='protected'),
]
