
package com.socialnetwork.checking_sn.ui.theme

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import com.socialnetwork.checking_sn.feature_auth.presentation.login.LoginScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterDetailsScreen
import com.socialnetwork.checking_sn.feature_post.presentation.create_post.CreatePostScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel
import com.socialnetwork.checking_sn.feature_splash.presentation.splash.SplashScreen



@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        navigation(
            route = AUTH_GRAPH_ROUTE,
            startDestination = Screen.LoginScreen.route
        ) {
            composable(Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(Screen.RegisterScreen.route) {
                RegisterScreen(navController = navController)
            }
            composable(Screen.RegisterDetailsScreen.route) {
                RegisterDetailsScreen(navController = navController)
            }
        }
        navigation(
            route = FEED_GRAPH_ROUTE,
            startDestination = Screen.FeedScreen.route
        ) {
            composable(Screen.FeedScreen.route) {
                val feedViewModel = hiltViewModel<FeedViewModel>()
                FeedScreen(
                    onNavigateToCreatePost = {
                        navController.navigate(Screen.CreatePostScreen.route)
                    },
                    viewModel = feedViewModel
                )
            }
            composable(Screen.CreatePostScreen.route) {
                val feedViewModel = hiltViewModel<FeedViewModel>(
                    navController.getBackStackEntry(Screen.FeedScreen.route)
                )
                CreatePostScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    feedViewModel = feedViewModel
                )
            }
        }
    }
}
