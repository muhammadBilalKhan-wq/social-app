package com.socialnetwork.checking_sn.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.feature_post.domain.models.PostUiModel
import com.socialnetwork.checking_sn.ui.theme.FooterTextColor
import com.socialnetwork.checking_sn.ui.theme.Spacing



@Composable
fun likeIcon(isLiked: Boolean): ImageVector {
    val color = if (isLiked) Color.Red else Color(0xFF7C3AED)
    return remember(color, isLiked) {
        ImageVector.Builder(
            name = "Like",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            if (isLiked) {
                // Filled heart
                path(
                    fill = SolidColor(color),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(12f, 21f)
                    lineTo(10.55f, 19.74f)
                    curveTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
                    curveTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
                    curveTo(9.24f, 3f, 10.91f, 3.81f, 12f, 5.09f)
                    curveTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
                    curveTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
                    curveTo(22f, 12.28f, 18.6f, 15.36f, 13.45f, 19.74f)
                    close()
                }
            } else {
                // Outline heart
                path(
                    fill = SolidColor(Color.Transparent),
                    fillAlpha = 1f,
                    stroke = SolidColor(color),
                    strokeAlpha = 1f,
                    strokeLineWidth = 2f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(12f, 21f)
                    lineTo(10.55f, 19.74f)
                    curveTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
                    curveTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
                    curveTo(9.24f, 3f, 10.91f, 3.81f, 12f, 5.09f)
                    curveTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
                    curveTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
                    curveTo(22f, 12.28f, 18.6f, 15.36f, 13.45f, 19.74f)
                    close()
                }
            }
        }.build()
    }
}

@Composable
fun bookmarkIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "Bookmark",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(19f, 21f)
                lineTo(12f, 16f)
                lineTo(5f, 21f)
                verticalLineTo(5f)
                lineTo(7f, 3f)
                horizontalLineTo(17f)
                lineTo(19f, 5f)
                close()
            }
        }.build()
    }
}

// Engagement icons as ImageVectors
@Composable
fun shareIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "Share",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(4f, 12f)
                verticalLineTo(20f)
                lineTo(6f, 22f)
                horizontalLineTo(18f)
                lineTo(20f, 20f)
                verticalLineTo(12f)
            }
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16f, 6f)
                lineTo(12f, 2f)
                lineTo(8f, 6f)
            }
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 2f)
                verticalLineTo(15f)
            }
        }.build()
    }
}

@Composable
fun commentIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "Comment",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(21f, 15f)
                lineTo(21f, 17f)
                lineTo(19f, 17f)
                horizontalLineTo(7f)
                lineTo(3f, 21f)
                verticalLineTo(5f)
                lineTo(5f, 3f)
                horizontalLineTo(19f)
                lineTo(21f, 5f)
                close()
            }
        }.build()
    }
}

@Composable
fun moreIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "More",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Top dot
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 11f)
                lineTo(13f, 11f)
                lineTo(13f, 13f)
                lineTo(11f, 13f)
                close()
            }
            // Middle dot
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 4f)
                lineTo(13f, 4f)
                lineTo(13f, 6f)
                lineTo(11f, 6f)
                close()
            }
            // Bottom dot
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 18f)
                lineTo(13f, 18f)
                lineTo(13f, 20f)
                lineTo(11f, 20f)
                close()
            }
        }.build()
    }
}

@Composable
fun PostItem(
    post: PostUiModel,
    onLikeClick: (String) -> Unit,
    onDoubleTapLike: (String) -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isCaptionExpanded by remember { mutableStateOf(false) }
    var showHeartAnimation by remember { mutableStateOf(false) }
    val heartScale = remember { Animatable(0f) }
    val heartAlpha = remember { Animatable(0f) }

    LaunchedEffect(showHeartAnimation) {
        if (showHeartAnimation) {
            heartScale.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
            heartAlpha.animateTo(1f, tween(150, easing = LinearEasing))
            heartAlpha.animateTo(0f, tween(150, easing = LinearEasing))
            heartScale.animateTo(0f, tween(300, easing = FastOutSlowInEasing))
            showHeartAnimation = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        // Post Image at top - spans full width of card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f/3f)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(Color.LightGray) // Placeholder shimmer
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            onDoubleTapLike(post.id)
                            showHeartAnimation = true
                        }
                    )
                }
        ) {
            AsyncImage(
                model = post.postImageUrl,
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )
            // Heart animation overlay
            if (heartScale.value > 0f) {
                Icon(
                    imageVector = likeIcon(true),
                    contentDescription = null,
                    tint = Color.White.copy(alpha = heartAlpha.value),
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(
                            scaleX = heartScale.value,
                            scaleY = heartScale.value
                        )
                )
            }
        }

        // Content section with padding
        Column(
            modifier = Modifier.padding(Spacing.Medium)
        ) {
            // Caption (Post text/content)
            val annotatedCaption = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${post.username} ")
                }
                append(post.caption)
            }
            Text(
                text = annotatedCaption,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                maxLines = if (isCaptionExpanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isCaptionExpanded = !isCaptionExpanded }
            )
            if (post.caption.length > 50 && !isCaptionExpanded) { // Rough check
                Text(
                    text = "more",
                    style = MaterialTheme.typography.bodyMedium.copy(color = FooterTextColor),
                    modifier = Modifier.clickable { isCaptionExpanded = true }
                )
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            // User profile section (avatar + username + timestamp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Image
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() }
                )
                Spacer(modifier = Modifier.width(Spacing.Small))
                // Username and timestamp
                Column {
                    Text(
                        text = post.username,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                    Text(
                        text = post.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = FooterTextColor
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                // More menu
                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = moreIcon(),
                        contentDescription = "More",
                        tint = FooterTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(Spacing.Medium))

            // Engagement row (like, comment, share, save)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onLikeClick(post.id) }) {
                    Icon(
                        imageVector = likeIcon(post.isLiked),
                        contentDescription = "Like",
                        tint = if (post.isLiked) Color.Red else Color(0xFF7C3AED)
                    )
                }
                IconButton(onClick = onCommentClick) {
                    Icon(
                        imageVector = commentIcon(),
                        contentDescription = "Comment",
                        tint = Color(0xFF7C3AED)
                    )
                }
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = shareIcon(),
                        contentDescription = "Share",
                        tint = Color(0xFF7C3AED)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        imageVector = bookmarkIcon(),
                        contentDescription = "Bookmark",
                        tint = Color(0xFF7C3AED)
                    )
                }
            }

            // Like Count
            Text(
                text = "Liked by ${post.likesCount} people",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(top = Spacing.Small)
            )
        }
    }
}

// Preview for testing
//@Preview(showBackground = true)
//@Composable
//fun PostItemPreview() {
//    PostItem(
//        post = PostData(),
//        onLikeClick = {},
//        onCommentClick = {},
//        onShareClick = {},
//        onBookmarkClick = {},
//        onProfileClick = {},
//        onMoreClick = {}
//    )
//}
