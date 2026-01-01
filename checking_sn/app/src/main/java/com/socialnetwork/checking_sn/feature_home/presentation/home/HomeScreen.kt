package com.socialnetwork.checking_sn.feature_home.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel(),
    onNavigateToComments: (String) -> Unit = {}
) {
    FeedScreen(
        onNavigateToCreatePost = { /* TODO */ },
        onNavigateToComments = onNavigateToComments,
        onSearchClick = { /* TODO */ },
        onProfileClick = { /* TODO */ },
        onSettingsClick = { /* TODO */ },
        viewModel = viewModel
    )
}
