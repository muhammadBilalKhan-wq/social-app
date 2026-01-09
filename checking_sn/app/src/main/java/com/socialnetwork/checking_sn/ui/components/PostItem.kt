package com.socialnetwork.checking_sn.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.feature_post.domain.models.PostUiModel
import com.socialnetwork.checking_sn.ui.theme.Spacing

@Composable
fun PostItem(
    post: PostUiModel,
    onLikeClick: (String) -> Unit,
    onDoubleTapLike: (String) -> Unit,
    onCommentClick: (String) -> Unit,
    onShareClick: (String) -> Unit,
    onBookmarkClick: (String) -> Unit,
    onProfileClick: (String) -> Unit,
    onMoreClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        shape = RoundedCornerShape(Spacing.Medium),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.ExtraSmall)
    ) {
        // Post image (optional) - full width, no padding
        if (post.postImageUrl.isNotEmpty()) {
            AsyncImage(
                model = post.postImageUrl,
                contentDescription = "Post image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = Spacing.Medium, topEnd = Spacing.Medium))
                    .clickable { onDoubleTapLike(post.id) },
                contentScale = ContentScale.Crop
            )
        }

        // Padded content below the image
        Column(modifier = Modifier.padding(Spacing.Medium)) {
            // Post content (text)
            if (post.caption.isNotEmpty()) {
                Text(
                    text = post.caption,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spacing.Small))
            }

            // Author section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile picture
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.profileImageUrl.ifEmpty { R.drawable.ic_launcher_foreground })
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(Spacing.IconSizeSmall + Spacing.ExtraSmall)
                        .clip(CircleShape)
                        .clickable { onProfileClick(post.id) },
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(R.drawable.ic_launcher_foreground)
                )

                Spacer(modifier = Modifier.width(Spacing.Small))

                // Username
                Text(
                    text = post.username,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                // Timestamp
                Text(
                    text = post.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = Spacing.Small)
                )

                // More options
                IconButton(onClick = { onMoreClick(post.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_more_outlined),
                        contentDescription = "More options"
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Small))

            // Engagement section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like button with count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onLikeClick(post.id) }) {
                        Icon(
                            painter = if (post.isLiked) {
                                painterResource(R.drawable.ic_heart_filled)
                            } else {
                                painterResource(R.drawable.ic_heart_outlined)
                            },
                            contentDescription = "Like",
                            tint = if (post.isLiked) Color(0xFFE91E63) else Color(0xFF666666)
                        )
                    }
                    if (post.likesCount > 0) {
                        Text(
                            text = post.likesCount.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Comment button
                IconButton(onClick = { onCommentClick(post.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = "Comment",
                        tint = Color(0xFF666666)
                    )
                }

                // Share button
                IconButton(onClick = { onShareClick(post.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "Share",
                        tint = Color(0xFF666666)
                    )
                }

                // Bookmark button
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onBookmarkClick(post.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bookmark),
                        contentDescription = "Bookmark",
                        tint = Color(0xFF666666)
                    )
                }
            }
        }
    }
}
