package com.socialnetwork.checking_sn.core.presentation.util

sealed class Screen(val route: String) {
    object AuthGate : Screen("auth_gate")
    object AuthScreen : Screen("auth_screen")
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object RegisterDetailsScreen : Screen("register_details_screen/{type}/{value}") {
        fun createRoute(type: String, value: String) = "register_details_screen/$type/$value"
    }
    object FeedScreen : Screen("feed_screen")
    object CreatePostScreen : Screen("create_post_screen")
    object CreateContentScreen : Screen("create_content_screen")
    object HomeScreen : Screen("home_screen")
    object ShortsScreen : Screen("shorts_screen")
    object NotificationsScreen : Screen("notifications_screen")
    object MoreScreen : Screen("more_screen")
}

const val AUTH_GRAPH_ROUTE = "auth_graph"
const val FEED_GRAPH_ROUTE = "feed_graph"
const val HOME_GRAPH_ROUTE = "home_graph"
