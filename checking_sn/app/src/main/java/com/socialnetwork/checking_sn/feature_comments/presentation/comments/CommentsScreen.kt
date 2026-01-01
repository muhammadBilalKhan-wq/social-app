package com.socialnetwork.checking_sn.feature_comments.presentation.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.feature_comments.domain.models.CommentUiModel
import com.socialnetwork.checking_sn.ui.theme.AppBackgroundColor

@Composable
fun CommentsScreen(
    postId: String, // Passed but not used yet
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Mock data for demonstration
    var comments by remember {
        mutableStateOf(
            listOf(
                CommentUiModel(
                    id = "1",
                    userId = "user1",
                    username = "alex_designer",
                    userAvatar = "",
                    text = "This is an amazing post! Love the composition 😍",
                    timestamp = "2h",
                    likesCount = 12,
                    isLiked = false
                ),
                CommentUiModel(
                    id = "2",
                    userId = "user2",
                    username = "sarah_photographer",
                    userAvatar = "",
                    text = "Beautiful lighting and colors! What's your editing process?",
                    timestamp = "1h",
                    likesCount = 8,
                    isLiked = true
                ),
                CommentUiModel(
                    id = "3",
                    userId = "user3",
                    username = "mike_traveler",
                    userAvatar = "",
                    text = "Where was this taken? Looks like paradise!",
                    timestamp = "45m",
                    likesCount = 5,
                    isLiked = false
                ),
                CommentUiModel(
                    id = "4",
                    userId = "user4",
                    username = "emma_artist",
                    userAvatar = "",
                    text = "Your photography skills are incredible. Keep it up! 🎨",
                    timestamp = "30m",
                    likesCount = 15,
                    isLiked = true
                )
            )
        )
    }

    Scaffold(
        containerColor = AppBackgroundColor,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add), // Using add icon as back for now
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Text(
                    text = "Comments",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            CommentInputBar(
                onSendComment = { commentText ->
                    // Optimistic UI: Add comment immediately
                    val newComment = CommentUiModel(
                        id = System.currentTimeMillis().toString(),
                        userId = "current_user",
                        username = "you",
                        userAvatar = "",
                        text = commentText,
                        timestamp = "now",
                        likesCount = 0,
                        isLiked = false
                    )
                    comments = listOf(newComment) + comments
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CommentsList(
                comments = comments,
                onCommentLikeClick = { commentId ->
                    // Optimistic UI: Update like state immediately
                    comments = comments.map { comment ->
                        if (comment.id == commentId) {
                            val newIsLiked = !comment.isLiked
                            comment.copy(
                                isLiked = newIsLiked,
                                likesCount = if (newIsLiked) comment.likesCount + 1 else comment.likesCount - 1
                            )
                        } else comment
                    }
                }
            )
        }
    }
}
