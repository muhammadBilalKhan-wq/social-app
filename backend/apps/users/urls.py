from django.urls import path
from .views import register_view, login_view, protected_view, get_me

urlpatterns = [
    path('register/', register_view, name='register'),
    path('login/', login_view, name='login'),
    path('me/', get_me, name='get_me'),
    path('protected/', protected_view, name='protected'),
]
