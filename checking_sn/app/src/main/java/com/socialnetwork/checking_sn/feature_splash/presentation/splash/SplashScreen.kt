package com.socialnetwork.checking_sn.feature_splash.presentation.splash

import androidx.compose.animation.core.*
<<<<<<< HEAD
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
=======
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
>>>>>>> origin/main
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
<<<<<<< HEAD
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
=======
import com.socialnetwork.checking_sn.R
>>>>>>> origin/main
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
<<<<<<< HEAD
fun SplashScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
=======
fun SplashScreen(
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }
>>>>>>> origin/main

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

<<<<<<< HEAD
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
=======
>>>>>>> origin/main
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
<<<<<<< HEAD
        // Centered app logo with animation
        Text(
            text = "Checking SN",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
=======

        // Carousel with images - COMMENTED OUT
        /*
        Column(
>>>>>>> origin/main
            modifier = Modifier
                .graphicsLayer(
                    scaleX = logoScale,
                    scaleY = logoScale,
                    alpha = logoAlpha
                )
        )
<<<<<<< HEAD
=======

        // Buttons at bottom center
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            // Log In button
            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SoftPeriwinkleBlue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Sign Up button
            Button(
                onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SoftLavender,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
>>>>>>> origin/main
    }
}
