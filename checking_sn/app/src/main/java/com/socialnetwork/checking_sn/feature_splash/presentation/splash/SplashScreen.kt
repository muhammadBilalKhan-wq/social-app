package com.socialnetwork.checking_sn.feature_splash.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.data.manager.SecureTokenManager
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun checkToken(secureTokenManager: SecureTokenManager): Boolean {
    delay(500L)
    return secureTokenManager.getAccessToken() != null
}

@Composable
fun SplashScreen(navController: NavController, secureTokenManager: SecureTokenManager) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val hasValidToken = checkToken(secureTokenManager)
                if (hasValidToken) {
                    navController.navigate(Screen.FeedScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.AuthScreen.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            } catch (e: Exception) {
                navController.navigate(Screen.AuthScreen.route) {
                    popUpTo(Screen.SplashScreen.route) { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo_notext),
            contentDescription = "App Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}
