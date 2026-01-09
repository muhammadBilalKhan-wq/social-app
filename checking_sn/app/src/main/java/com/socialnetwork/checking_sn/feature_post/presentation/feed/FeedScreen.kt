package com.socialnetwork.checking_sn.feature_post.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.feature_post.domain.models.PostUiModel
import com.socialnetwork.checking_sn.ui.theme.Spacing
import com.socialnetwork.checking_sn.ui.components.PostItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FeedScreen(
    onNavigateToCreatePost: () -> Unit,
    onNavigateToComments: (String) -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: FeedViewModel
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    when {
        state.isInitialLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error?.asString(context) ?: "Unknown error",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F7))
                    .padding(top = Spacing.XXXLarge),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(state.posts) { post ->
                    PostItem(
                        post = post.toPostUiModel(),
                        onLikeClick = { postId ->
                            viewModel.onLikeClicked(postId)
                        },
                        onDoubleTapLike = { postId ->
                            viewModel.onLikeClicked(postId)
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
    }
}

private fun Post.toPostUiModel(): PostUiModel {
    return PostUiModel(
        id = this.id,
        username = this.username,
        profileImageUrl = "", // Not implemented yet
        postImageUrl = this.imageUrl,
        caption = this.description,
        likesCount = this.likeCount,
        isLiked = this.isLiked,
        timestamp = formatTimestamp(this.timestamp)
    )
}

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60 * 1000 -> "Just now"
        diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)} minutes ago"
        diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)} hours ago"
        diff < 7 * 24 * 60 * 60 * 1000 -> "${diff / (24 * 60 * 60 * 1000)} days ago"
        else -> {
            val date = Date(timestamp)
            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(date)
        }
    }
}
