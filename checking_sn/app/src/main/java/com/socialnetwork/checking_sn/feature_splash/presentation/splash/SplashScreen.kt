package com.socialnetwork.checking_sn.feature_splash.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Sample suspend function to check for a valid stored token.
 * In a real app, this would check secure storage for a JWT token
 * and validate it with your backend API.
 */
suspend fun checkToken(): Boolean {
    // Simulate network/database delay
    delay(500L) // Reduced for faster testing

    // In real implementation, check:
    // 1. Retrieve token from secure storage
    // 2. Validate token format
    // 3. Optionally verify with backend
    // 4. Return true if valid, false otherwise

    // For demo: always return false to go to auth (easier testing)
    return false
}

@Composable
fun SplashScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    // Logo animation state
    var logoVisible by remember { mutableStateOf(false) }
    val logoScale by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoScale"
    )
    val logoAlpha by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutCubic),
        label = "logoAlpha"
    )

    // Start logo animation when screen appears
    LaunchedEffect(Unit) {
        logoVisible = true
    }

    // Perform token check asynchronously
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val hasValidToken = checkToken()

                if (hasValidToken) {
                    // Navigate to Home screen (Feed)
                    navController.navigate(FEED_GRAPH_ROUTE) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                } else {
                    // Navigate to Auth screen
                    navController.navigate(Screen.AuthScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                // On error, default to auth screen
                navController.navigate(Screen.AuthScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            }
        }
    }

    // Full-screen white background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Centered app logo with animation
        Text(
            text = "Checking SN",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = logoScale,
                    scaleY = logoScale,
                    alpha = logoAlpha
                )
        )
    }
}
