package com.socialnetwork.checking_sn.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.HOME_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import com.socialnetwork.checking_sn.feature_auth.presentation.auth.AuthScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.login.LoginScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterEvent
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterDetailsScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterScreen
import com.socialnetwork.checking_sn.feature_auth.presentation.register.RegisterViewModel
import com.socialnetwork.checking_sn.feature_authgate.presentation.authgate.AuthGateScreen
import com.socialnetwork.checking_sn.feature_comments.presentation.comments.CommentsScreen
import com.socialnetwork.checking_sn.feature_create.presentation.create.CreateContentScreen
import com.socialnetwork.checking_sn.feature_home.presentation.home.HomeScreen
import com.socialnetwork.checking_sn.feature_more.presentation.more.MoreScreen
import com.socialnetwork.checking_sn.feature_notifications.presentation.notifications.NotificationsScreen
import com.socialnetwork.checking_sn.feature_post.presentation.create_post.CreatePostScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedScreen
import com.socialnetwork.checking_sn.feature_post.presentation.feed.FeedViewModel

import com.socialnetwork.checking_sn.feature_shorts.presentation.shorts.ShortsScreen
import com.socialnetwork.checking_sn.feature_splash.presentation.splash.SplashScreen
import com.socialnetwork.checking_sn.feature_search.presentation.search.SearchScreen
import com.socialnetwork.checking_sn.ui.components.BottomNavigationBar
import com.socialnetwork.checking_sn.ui.components.TopNavigationBar


@Composable
fun Navigation(
    secureTokenManager: SecureTokenManager
) {
    val navController = rememberNavController()
    val isLoggedIn by secureTokenManager.isLoggedIn.collectAsState(initial = false)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Handle logout navigation
    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            // If not logged in and not already on auth screens, navigate to login
            if (currentRoute != null && !currentRoute.startsWith(AUTH_GRAPH_ROUTE) &&
                currentRoute != Screen.AuthGate.route && currentRoute != Screen.SplashScreen.route) {
                navController.navigate(AUTH_GRAPH_ROUTE) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    // Show top navigation on main screens when logged in
    val mainRoutes = listOf(
        Screen.HomeScreen.route,
        Screen.ShortsScreen.route,
        Screen.NotificationsScreen.route,
        Screen.MoreScreen.route,
        Screen.FeedScreen.route
    )
    val showTopBar = remember(currentRoute, isLoggedIn) {
        isLoggedIn && (currentRoute in mainRoutes)
    }

    val showBottomBar = remember(currentRoute, isLoggedIn) {
        isLoggedIn && (currentRoute in mainRoutes)
    }

    val selectedTab = when (currentRoute) {
        Screen.HomeScreen.route, Screen.FeedScreen.route -> "home"
        Screen.ShortsScreen.route -> "shorts"
        Screen.NotificationsScreen.route -> "notifications"
        Screen.MoreScreen.route -> "more"
        else -> null
    }



    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(Screen.AuthGate.route) {
                    AuthGateScreen(navController = navController)
                }
                composable(Screen.SplashScreen.route) {
                    SplashScreen(navController = navController, secureTokenManager = secureTokenManager)
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
                            onNavigateToComments = { postId ->
                                navController.navigate(Screen.CommentsScreen.createRoute(postId))
                            },
                            onSearchClick = { /* TODO */ },
                            onProfileClick = { /* TODO */ },
                            onSettingsClick = { /* TODO */ },
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
                        HomeScreen(
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToComments = { postId ->
                                navController.navigate(Screen.CommentsScreen.createRoute(postId))
                            }
                        )
                    }
                    composable(Screen.ShortsScreen.route) {
                        ShortsScreen(modifier = Modifier.fillMaxSize())
                    }
                    composable(Screen.NotificationsScreen.route) {
                        NotificationsScreen(modifier = Modifier.fillMaxSize())
                    }
                    composable(Screen.MoreScreen.route) {
                        MoreScreen(modifier = Modifier.fillMaxSize())
                    }
                    composable(Screen.CreateContentScreen.route) {
                        CreateContentScreen()
                    }
                }
                composable(Screen.SearchScreen.route) {
                    SearchScreen(
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onResultClick = { result ->
                            // TODO: Navigate to user profile or post detail based on result type
                            // For now, just navigate back
                            navController.navigateUp()
                        }
                    )
                }
                composable(Screen.ProfileScreen.route) {
                    androidx.compose.material3.Text("Profile Screen")
                }
                composable(
                    route = Screen.CommentsScreen.route,
                    arguments = listOf(
                        navArgument("postId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val postId = backStackEntry.arguments?.getString("postId") ?: ""
                    CommentsScreen(
                        postId = postId,
                        onNavigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }

        // Floating top navigation overlay
        if (showTopBar) {
            TopNavigationBar(
                modifier = Modifier.align(Alignment.TopCenter),
                onSearchClick = {
                    navController.navigate(Screen.SearchScreen.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.ProfileScreen.route)
                }
            )
        }

        // Floating bottom navigation overlay
        if (showBottomBar) {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onHomeClick = { navController.navigate(Screen.HomeScreen.route) },
                onShortsClick = { navController.navigate(Screen.ShortsScreen.route) },
                onNotificationsClick = { navController.navigate(Screen.NotificationsScreen.route) },
                onMoreClick = { navController.navigate(Screen.MoreScreen.route) },
                onAddClick = { 
                    // Navigate to Feed screen first, then to CreatePostScreen
                    navController.navigate(Screen.FeedScreen.route) {
                        popUpTo(Screen.FeedScreen.route) { inclusive = false }
                    }
                    // Then navigate to CreatePostScreen
                    navController.navigate(Screen.CreatePostScreen.route)
                },
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
            )
        }
    }
}
