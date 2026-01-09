package com.socialnetwork.checking_sn.feature_post.presentation.create_post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.components.StandardTextField
import com.socialnetwork.checking_sn.core.presentation.components.buttons.PrimaryActionButton
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreatePostScreen(
    onNavigateBack: () -> Unit,
    feedViewModel: FeedViewModel,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with back button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with proper back icon
                        contentDescription = "Back"
                    )
                }
                Text(
                    text = "Create Post",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Error message
            state.error?.let { error ->
                Text(
                    text = error.asString(context),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Content input
            StandardTextField(
                text = state.content,
                onValueChange = {
                    viewModel.onEvent(CreatePostEvent.EnteredContent(it))
                },
                hint = "What's on your mind?",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )

            // Image picker component
            ImagePickerComponent(
                selectedImageUri = state.selectedImageUri,
                onImageSelected = { uri ->
                    viewModel.onEvent(CreatePostEvent.SelectedImage(uri))
                },
                onRemoveImage = {
                    viewModel.onEvent(CreatePostEvent.RemoveImage)
                }
            )

            Spacer(modifier = Modifier.requiredHeight(0.dp))

            // Post button
            PrimaryActionButton(
                text = "Post",
                onClick = {
                    viewModel.onEvent(CreatePostEvent.Post)
                },
                enabled = state.content.isNotBlank() && !state.isSubmitting,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Loading overlay
        if (state.isSubmitting) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
