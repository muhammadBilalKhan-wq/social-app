package com.socialnetwork.checking_sn.feature_comments.presentation.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.feature_comments.domain.models.CommentUiModel

@Composable
fun CommentsList(
    comments: List<CommentUiModel>,
    onCommentLikeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(comments) { comment ->
            CommentItem(
                comment = comment,
                onLikeClick = onCommentLikeClick
            )
        }
    }
}
