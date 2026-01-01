package com.socialnetwork.checking_sn.core.presentation.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object RegisterDetailsScreen : Screen("register_details_screen/{type}/{value}") {
        fun createRoute(type: String, value: String) = "register_details_screen/$type/$value"
    }
    object FeedScreen : Screen("feed_screen")
    object CreatePostScreen : Screen("create_post_screen")
<<<<<<< HEAD
    object CreateContentScreen : Screen("create_content_screen")
    object HomeScreen : Screen("home_screen")
    object ShortsScreen : Screen("shorts_screen")
    object NotificationsScreen : Screen("notifications_screen")
    object MoreScreen : Screen("more_screen")
    object SearchScreen : Screen("search_screen")
    object ProfileScreen : Screen("profile_screen")
    object CommentsScreen : Screen("comments/{postId}") {
        fun createRoute(postId: String) = "comments/$postId"
    }
=======
>>>>>>> origin/main
}

const val AUTH_GRAPH_ROUTE = "auth_graph"
const val FEED_GRAPH_ROUTE = "feed_graph"
