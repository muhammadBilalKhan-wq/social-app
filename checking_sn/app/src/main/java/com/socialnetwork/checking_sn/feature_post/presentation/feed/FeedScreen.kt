package com.socialnetwork.checking_sn.feature_post.presentation.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.socialnetwork.checking_sn.core.presentation.components.StandardTextField
import com.socialnetwork.checking_sn.core.presentation.components.buttons.PrimaryActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.socialnetwork.checking_sn.core.domain.models.Post
import com.socialnetwork.checking_sn.ui.components.TopNavigationBar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun FeedScreen(
    onNavigateToCreatePost: () -> Unit,
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: FeedViewModel
) {
    val state = viewModel.state.value
    val listState = rememberLazyListState()

    // Monitor scroll position and trigger load more when near end
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .filter { lastIndex ->
                val shouldLoad = lastIndex != null &&
                    lastIndex >= state.posts.size - 2 &&
                    state.hasMorePosts &&
                    !state.isLoadingMore
                shouldLoad
            }
            .collect {
                viewModel.onEvent(FeedEvent.LoadMorePosts)
            }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreatePost) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create Post"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isInitialLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = state.error.asString(LocalContext.current))
                }
            } else if (state.posts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No posts yet")
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.posts) { post ->
                        PostItem(
                            post = post,
                            onLikeClicked = viewModel::onLikeClicked,
                            onToggleComments = { viewModel.onEvent(FeedEvent.ToggleComments(post.id)) },
                            onAddComment = { content -> viewModel.onEvent(FeedEvent.AddComment(post.id, content)) }
                        )
                    }

                    // Show loading indicator at bottom when loading more posts
                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Loading more posts...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // Show end of feed message when no more posts
                    if (!state.hasMorePosts && !state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "You've reached the end! (${state.posts.size} total posts)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable that displays a single post item in the feed.
 * Shows the post content, username, like count, comments section, and interaction buttons.
 *
 * @param post The post data to display
 * @param onLikeClicked Callback function called when the like button is clicked
 * @param onToggleComments Callback function called when the comments toggle button is clicked
 * @param onAddComment Callback function called when adding a new comment
 * @param modifier Modifier for styling the composable
 */
@Composable
fun PostItem(
    post: Post,
    onLikeClicked: (String) -> Unit,
    onToggleComments: () -> Unit,
    onAddComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Post header with username and like button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = post.username,
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(
                    onClick = { onLikeClicked(post.id) }
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (post.isLiked) "Unlike" else "Like"
                    )
                }
            }

            // Post content
            Text(
                text = post.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Engagement stats and comments toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${post.likeCount} likes • ${post.commentCount} comments",
                    style = MaterialTheme.typography.bodySmall
                )
                TextButton(onClick = onToggleComments) {
                    Text(
                        text = if (post.isCommentsExpanded) "Hide comments" else "Show comments (${post.commentCount})"
                    )
                }
            }

            // Comments section - only shown when expanded
            if (post.isCommentsExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                // Existing comments
                post.comments.forEach { comment ->
                    CommentItem(comment = comment)
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Add comment input
                AddCommentSection(onAddComment = onAddComment)
            }
        }
    }
}

/**
 * Composable that displays a single comment.
 */
@Composable
fun CommentItem(comment: com.socialnetwork.checking_sn.core.domain.models.Comment) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = comment.username,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * Composable for adding a new comment to a post.
 */
@Composable
fun AddCommentSection(onAddComment: (String) -> Unit) {
    var commentText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StandardTextField(
            text = commentText,
            onValueChange = { commentText = it },
            hint = "Add a comment...",
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryActionButton(
            text = "Post",
            onClick = {
                if (commentText.isNotBlank()) {
                    onAddComment(commentText)
                    commentText = ""
                }
            },
            enabled = commentText.isNotBlank()
        )
    }
}
