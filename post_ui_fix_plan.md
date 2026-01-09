# Post UI Fix Plan

## Issues Identified:
1. **Poor Icon Selection**: Using generic system icons instead of Material Design icons
2. **Hardcoded System Drawables**: Using `android.R.drawable.*` resources that look unprofessional
3. **Missing Custom Drawables**: Not utilizing the custom drawable resources in the project
4. **Basic Image Loading**: AsyncImage implementation lacks proper error handling and loading states
5. **Inconsistent Styling**: Mixed use of system and custom styling approaches

## Available Drawable Resources:
- ic_add.xml
- ic_google_logo.png  
- ic_home_filled.xml
- ic_home_outlined.xml
- ic_more_filled.xml
- ic_more_outlined.xml
- ic_notifications_filled.xml
- ic_notifications_outlined.xml
- ic_shorts_filled.xml
- ic_shorts_outlined.xml

## Fix Steps:
- [x] Add missing Material Design icons for post actions (like, comment, share, bookmark)
- [x] Create ic_heart_outlined.xml
- [x] Create ic_heart_filled.xml
- [x] Create ic_comment.xml
- [x] Create ic_share.xml
- [x] Create ic_bookmark.xml
- [x] Update PostItem.kt to use proper Material Design icons
- [x] Improve styling with consistent Material Design colors
- [x] Replace hardcoded system drawables with custom resources
- [ ] Add loading states and improved error handling for images
- [ ] Test the updated component in the feed screen

## Icons Created:
- ✅ Heart/Like icons (filled and outline)
- ✅ Comment/Chat icon
- ✅ Share icon
- ✅ Bookmark/Save icon

## Key Improvements Made:
- Replaced `android.R.drawable.*` with custom Material Design icons
- Added proper pink color tint for liked posts (#FFE91E63)
- Used `ic_more_outlined` for the more options button
- Improved profile image placeholder handling
- Enhanced visual consistency throughout the component
