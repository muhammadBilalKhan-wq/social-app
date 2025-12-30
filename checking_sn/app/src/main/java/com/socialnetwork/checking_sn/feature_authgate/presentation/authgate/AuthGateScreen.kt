package com.socialnetwork.checking_sn.feature_authgate.presentation.authgate

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.HOME_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthGateScreen(
    navController: NavController,
    viewModel: AuthGateViewModel = hiltViewModel()
) {

    // Handle navigation events from ViewModel
    LaunchedEffect(viewModel) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthGateEvent.NavigateToFeed -> {
                    // Navigate to home screen with bottom navigation and clear back stack
                    navController.navigate(HOME_GRAPH_ROUTE) {
                        popUpTo(Screen.AuthGate.route) { inclusive = true }
                    }
                }
                is AuthGateEvent.NavigateToAuth -> {
                    // Navigate to auth screens and clear back stack
                    navController.navigate(AUTH_GRAPH_ROUTE) {
                        popUpTo(Screen.AuthGate.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Static background elements
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Gray.copy(alpha = 0.05f),
                radius = 40f,
                center = Offset(100f, 300f)
            )
            drawCircle(
                color = Color.Gray.copy(alpha = 0.03f),
                radius = 60f,
                center = Offset(300f, 500f)
            )
            drawCircle(
                color = Color.Gray.copy(alpha = 0.04f),
                radius = 50f,
                center = Offset(500f, 200f)
            )
        }

        // Logo at center
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Loading indicator
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
