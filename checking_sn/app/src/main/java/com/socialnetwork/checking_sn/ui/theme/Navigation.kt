
package com.socialnetwork.checking_sn.ui.theme

import androidx.compose.runtime.Composable
<<<<<<< HEAD
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
=======
>>>>>>> parent of b8f2250 (Working Fine)
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import com.socialnetwork.checking_sn.feature_auth.presentation.login.LoginScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterEvent
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterDetailsScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterViewModel
import com.socialnetwork.checking_sn.feature_post.presentation.create_post.CreatePostScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel
import com.socialnetwork.checking_sn.feature_splash.presentation.splash.SplashScreen
<<<<<<< HEAD
import com.socialnetwork.checking_sn.ui.components.StandardScaffold
=======


>>>>>>> parent of b8f2250 (Working Fine)

@Composable
fun Navigation() {
    val navController = rememberNavController()
<<<<<<< HEAD
    val isLoggedIn by secureTokenManager.isLoggedIn.collectAsState(initial = false)

    androidx.compose.runtime.LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            val currentRoute = navController.currentDestination?.route
            if (currentRoute != null && !currentRoute.startsWith(AUTH_GRAPH_ROUTE) &&
                currentRoute != Screen.AuthGate.route && currentRoute != Screen.SplashScreen.route) {
                navController.navigate(AUTH_GRAPH_ROUTE) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

=======
>>>>>>> parent of b8f2250 (Working Fine)
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
                val registerViewModel = hiltViewModel<RegisterViewModel>()
                RegisterScreen(navController = navController, viewModel = registerViewModel)
            }
            composable(
                route = Screen.RegisterDetailsScreen.route,
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType },
                    navArgument("value") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: "email"
                val value = backStackEntry.arguments?.getString("value") ?: ""
                val registerViewModel = hiltViewModel<RegisterViewModel>()

                when (type) {
                    "email" -> registerViewModel.onEvent(RegisterEvent.EnteredEmail(value))
                    "phone" -> registerViewModel.onEvent(RegisterEvent.EnteredPhoneNumber(value))
                }
                registerViewModel.onEvent(RegisterEvent.SelectedOption(if (type == "email") "Email" else "Phone"))

                RegisterDetailsScreen(navController = navController, viewModel = registerViewModel)
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
<<<<<<< HEAD
            composable(Screen.CreatePostScreen.route) { backStackEntry ->
                val parentEntry = androidx.compose.runtime.remember(backStackEntry) {
=======
            composable(Screen.CreatePostScreen.route) {
                val feedViewModel = hiltViewModel<FeedViewModel>(
>>>>>>> parent of b8f2250 (Working Fine)
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
<<<<<<< HEAD
        navigation(
            route = HOME_GRAPH_ROUTE,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(Screen.HomeScreen.route) {
                StandardScaffold(navController = navController) {
                    HomeScreen()
                }
            }
            composable(Screen.ShortsScreen.route) {
                StandardScaffold(navController = navController) {
                    ShortsScreen()
                }
            }
            composable(Screen.NotificationsScreen.route) {
                StandardScaffold(navController = navController) {
                    NotificationsScreen()
                }
            }
            composable(Screen.MoreScreen.route) {
                StandardScaffold(navController = navController) {
                    MoreScreen()
                }
            }
            composable(Screen.CreateContentScreen.route) {
                CreateContentScreen()
            }
        }
=======
>>>>>>> parent of b8f2250 (Working Fine)
    }
}
