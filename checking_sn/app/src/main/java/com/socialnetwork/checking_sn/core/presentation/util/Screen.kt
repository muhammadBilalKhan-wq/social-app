package com.socialnetwork.checking_sn.core.presentation.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object FeedScreen : Screen("feed_screen")
    object CreatePostScreen : Screen("create_post_screen")
}

const val AUTH_GRAPH_ROUTE = "auth_graph"
const val FEED_GRAPH_ROUTE = "feed_graph"
