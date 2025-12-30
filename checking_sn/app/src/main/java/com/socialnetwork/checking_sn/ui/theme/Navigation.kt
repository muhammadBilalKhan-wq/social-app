
package com.socialnetwork.checking_sn.ui.theme

import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
//import androidx.compose.ui.unit.dp
//import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.HOME_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import com.socialnetwork.checking_sn.feature_auth.presentation.auth.AuthScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.login.LoginScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterEvent
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterDetailsScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterViewModel
import com.socialnetwork.checking_sn.feature_authgate.presentation.authgate.AuthGateScreen
import com.socialnetwork.checking_sn.feature_create.presentation.create.CreateContentScreen
import com.socialnetwork.checking_sn.feature_home.presentation.home.HomeScreen
import com.socialnetwork.checking_sn.feature_more.presentation.more.MoreScreen
import com.socialnetwork.checking_sn.feature_notifications.presentation.notifications.NotificationsScreen
import com.socialnetwork.checking_sn.feature_post.presentation.create_post.CreatePostScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel
import com.socialnetwork.checking_sn.feature_shorts.presentation.shorts.ShortsScreen
import com.socialnetwork.checking_sn.feature_splash.presentation.splash.SplashScreen
//import com.socialnetwork.checking_sn.ui.components.BottomNavBar
import com.socialnetwork.checking_sn.ui.components.CustomBottomBar
//import com.socialnetwork.checking_sn.ui.components.FloatingAddButton



@Composable
fun Navigation(
    secureTokenManager: SecureTokenManager
) {
    val navController = rememberNavController()
    val isLoggedIn by secureTokenManager.isLoggedIn.collectAsState(initial = false)

    // Handle logout navigation
    androidx.compose.runtime.LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            // If not logged in and not already on auth screens, navigate to login
            val currentRoute = navController.currentDestination?.route
            if (currentRoute != null && !currentRoute.startsWith(AUTH_GRAPH_ROUTE) &&
                currentRoute != Screen.AuthGate.route && currentRoute != Screen.SplashScreen.route) {
                navController.navigate(AUTH_GRAPH_ROUTE) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.AuthGate.route
    ) {
        composable(Screen.AuthGate.route) {
            AuthGateScreen(navController = navController)
        }
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        navigation(
            route = AUTH_GRAPH_ROUTE,
            startDestination = Screen.AuthScreen.route
        ) {
            composable(Screen.AuthScreen.route) {
                AuthScreen(navController = navController)
            }
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

                // Set the appropriate field in the ViewModel based on type
                when (type) {
                    "email" -> registerViewModel.onEvent(RegisterEvent.EnteredEmail(value))
                    "phone" -> registerViewModel.onEvent(RegisterEvent.EnteredPhoneNumber(value))
                }
                // Also set the selected option
                registerViewModel.onEvent(RegisterEvent.SelectedOption(if (type == "email") "Email" else "Phone"))

                RegisterDetailsScreen(navController = navController, viewModel = registerViewModel)
            }
        }
        navigation(
            route = FEED_GRAPH_ROUTE,
            startDestination = Screen.FeedScreen.route
        ) {
            composable(Screen.FeedScreen.route) { backStackEntry ->
                val feedViewModel = hiltViewModel<FeedViewModel>(backStackEntry)
                FeedScreen(
                    onNavigateToCreatePost = {
                        navController.navigate(Screen.CreatePostScreen.route)
                    },
                    viewModel = feedViewModel
                )
            }
            composable(Screen.CreatePostScreen.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Screen.FeedScreen.route)
                }
                val feedViewModel = hiltViewModel<FeedViewModel>(parentEntry)
                CreatePostScreen(
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    feedViewModel = feedViewModel
                )
            }
        }
        navigation(
            route = HOME_GRAPH_ROUTE,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(Screen.HomeScreen.route) {
                Box(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(modifier = Modifier.fillMaxSize())
                    CustomBottomBar(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
            composable(Screen.ShortsScreen.route) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ShortsScreen(modifier = Modifier.fillMaxSize())
                    CustomBottomBar(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
            composable(Screen.NotificationsScreen.route) {
                Box(modifier = Modifier.fillMaxSize()) {
                    NotificationsScreen(modifier = Modifier.fillMaxSize())
                    CustomBottomBar(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
            composable(Screen.MoreScreen.route) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MoreScreen(modifier = Modifier.fillMaxSize())
                    CustomBottomBar(
                        navController = navController,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
            composable(Screen.CreateContentScreen.route) {
                CreateContentScreen()
            }
        }
    }
}
