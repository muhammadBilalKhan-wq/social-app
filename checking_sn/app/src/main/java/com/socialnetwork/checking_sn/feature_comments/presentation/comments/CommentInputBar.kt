package com.socialnetwork.checking_sn.feature_comments.presentation.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.ui.theme.Spacing

@Composable
fun CommentInputBar(
    onSendComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var commentText by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Avatar
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
            contentDescription = "Your Avatar",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(Spacing.Medium))

        // Text Input
        Box(
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .background(
                    color = Color(0xFFF0F0F0),
                    shape = CircleShape
                )
                .padding(horizontal = Spacing.Medium),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = commentText,
                onValueChange = { commentText = it },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp
                ),
                cursorBrush = SolidColor(Color.Black),
                decorationBox = { innerTextField ->
                    if (commentText.isEmpty()) {
                        Text(
                            text = "Add a comment...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray
                            )
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.width(Spacing.Small))

        // Send Button
        IconButton(
            onClick = {
                if (commentText.isNotBlank()) {
                    onSendComment(commentText.trim())
                    commentText = ""
                }
            },
            enabled = commentText.isNotBlank(),
            modifier = Modifier.size(36.dp)
        ) {
            Text(
                text = "Post",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = if (commentText.isNotBlank()) Color(0xFF0095F6) else Color.Gray,
                    fontSize = 14.sp
                )
            )
        }
    }
}
