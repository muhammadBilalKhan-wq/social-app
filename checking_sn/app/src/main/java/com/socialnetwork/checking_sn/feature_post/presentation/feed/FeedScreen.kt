package com.socialnetwork.checking_sn.feature_post.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.feature_post.domain.models.PostUiModel
import com.socialnetwork.checking_sn.ui.theme.Spacing
import com.socialnetwork.checking_sn.ui.components.PostItem

@Composable
fun FeedScreen(
    onNavigateToCreatePost: () -> Unit,
    onNavigateToComments: (String) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: FeedViewModel
) {
    val posts = remember {
        mutableStateOf(
            listOf(
            PostUiModel(
                id = "1",
                username = "john_doe",
                profileImageUrl = "",
                postImageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&q=80",
                caption = "This is a sample caption for the post. It can be longer and will be truncated to 2 lines initially.",
                likesCount = 42,
                isLiked = false,
                timestamp = "2 hours ago"
            ),
            PostUiModel(
                id = "2",
                username = "jane_smith",
                profileImageUrl = "",
                postImageUrl = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&q=80",
                caption = "Another post with different content to show variety in the feed.",
                likesCount = 15,
                isLiked = true,
                timestamp = "1 day ago"
            ),
            PostUiModel(
                id = "3",
                username = "traveler_123",
                profileImageUrl = "",
                postImageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&q=80",
                caption = "Exploring new places! This is an amazing journey.",
                likesCount = 87,
                isLiked = false,
                timestamp = "3 hours ago"
            ),
            PostUiModel(
                id = "4",
                username = "foodie_girl",
                profileImageUrl = "",
                postImageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=800&q=80",
                caption = "Delicious food from around the world. Can't get enough!",
                likesCount = 23,
                isLiked = true,
                timestamp = "5 hours ago"
            ),
            PostUiModel(
                id = "5",
                username = "tech_guru",
                profileImageUrl = "",
                postImageUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=800&q=80",
                caption = "Latest tech trends and innovations. The future is here!",
                likesCount = 56,
                isLiked = false,
                timestamp = "1 hour ago"
            )
        ))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
            .padding(top = Spacing.XXLarge, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(posts.value) { post ->
            PostItem(
                post = post,
                onLikeClick = { postId ->
                    posts.value = posts.value.map {
                        if (it.id == postId) {
                            val newIsLiked = !it.isLiked
                            it.copy(isLiked = newIsLiked, likesCount = if (newIsLiked) it.likesCount + 1 else it.likesCount - 1)
                        } else it
                    }
                },
                onDoubleTapLike = { postId ->
                    posts.value = posts.value.map {
                        if (it.id == postId && !it.isLiked) {
                            it.copy(isLiked = true, likesCount = it.likesCount + 1)
                        } else it
                    }
                },
                onCommentClick = { onNavigateToComments(post.id) },
                onShareClick = { /* TODO */ },
                onBookmarkClick = { /* TODO */ },
                onProfileClick = { /* TODO */ },
                onMoreClick = { /* TODO */ }
            )
        }
    }
}
