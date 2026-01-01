package com.socialnetwork.checking_sn.feature_comments.presentation.comments

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.feature_comments.domain.models.CommentUiModel
import com.socialnetwork.checking_sn.ui.theme.FooterTextColor
import com.socialnetwork.checking_sn.ui.theme.Spacing

@Composable
fun commentLikeIcon(isLiked: Boolean): ImageVector {
    val color = if (isLiked) Color.Red else Color(0xFF7C3AED)
    return remember(color, isLiked) {
        ImageVector.Builder(
            name = "CommentLike",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            if (isLiked) {
                // Filled heart (smaller for comments)
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
                // Outline heart (smaller for comments)
                path(
                    fill = SolidColor(Color.Transparent),
                    fillAlpha = 1f,
                    stroke = SolidColor(color),
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.5f,
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
fun CommentItem(
    comment: CommentUiModel,
    onLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(Spacing.Medium))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Username + Comment text
            val annotatedComment = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${comment.username} ")
                }
                append(comment.text)
            }

            Text(
                text = annotatedComment,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(Spacing.ExtraSmall))

            // Like count + timestamp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.Medium)
            ) {
                Text(
                    text = comment.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = FooterTextColor
                )

                if (comment.likesCount > 0) {
                    Text(
                        text = "${comment.likesCount} ${if (comment.likesCount == 1) "like" else "likes"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = FooterTextColor
                    )
                }
            }
        }

        // Like button
        IconButton(
            onClick = { onLikeClick(comment.id) },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = commentLikeIcon(comment.isLiked),
                contentDescription = "Like",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
