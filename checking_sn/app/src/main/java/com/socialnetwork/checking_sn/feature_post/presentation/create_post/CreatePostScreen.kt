package com.socialnetwork.checking_sn.feature_post.presentation.create_post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.socialnetwork.checking_sn.core.presentation.components.StandardTextField
import com.socialnetwork.checking_sn.core.presentation.components.buttons.PrimaryActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreatePostScreen(
    onNavigateBack: () -> Unit,
    feedViewModel: FeedViewModel,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CreatePostUiEvent.PostCreated -> {
                    feedViewModel.addPost(event.post)
                    onNavigateBack()
                }
            }
        }
    }

    if (state.isSubmitting) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Create Post",
                style = MaterialTheme.typography.headlineMedium
            )
            StandardTextField(
                text = state.content,
                onValueChange = {
                    viewModel.onEvent(CreatePostEvent.EnteredContent(it))
                },
                hint = "Content",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )
            PrimaryActionButton(
                text = "Post",
                onClick = {
                    viewModel.onEvent(CreatePostEvent.Post)
                },
                enabled = state.content.isNotBlank()
            )
        }
    }
}
