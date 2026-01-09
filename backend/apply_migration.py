#!/usr/bin/env python3
"""
Script to apply the migration directly
"""
import os
import sys
import django

# Add the current directory to the Python path
sys.path.append('/home/mb/AndroidStudioProjects/app/social-app/backend')

# Set up Django settings
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'core.settings.dev')
django.setup()

from django.core.management import execute_from_command_line
from django.db import connection

print("Applying migration for posts app...")

try:
    # Try to apply the specific migration
    with connection.schema_editor() as schema_editor:
        from django.db import migrations
        from apps.posts.models import Post
        
        # This will apply the migration we created
        schema_editor.alter_field(
            Post,
            'image_url',
            old_field=models.TextField(blank=True, null=True),
            new_field=models.TextField(blank=True, null=True)
        )
    
    print("Migration applied successfully!")
    
except Exception as e:
    print(f"Error applying migration: {e}")
    print("Migration may have already been applied or there was an issue.")
