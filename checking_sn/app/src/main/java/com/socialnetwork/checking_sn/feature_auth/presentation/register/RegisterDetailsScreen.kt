package com.socialnetwork.checking_sn.feature_auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.core.presentation.components.TextInputField
import com.socialnetwork.checking_sn.core.presentation.components.PasswordInputField
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE

@Composable
fun RegisterDetailsScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.OnRegister -> {
                    navController.navigate(FEED_GRAPH_ROUTE) {
                        popUpTo(AUTH_GRAPH_ROUTE) { inclusive = true }
                    }
                }
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background shapes (matching LoginScreen)
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Large circle top-left
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = (-50).dp, y = (-50).dp)
                    .background(Color(0xFFE0E0E0).copy(alpha = 0.3f), shape = CircleShape)
            )
            // Medium circle bottom-right
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = 250.dp, y = 400.dp)
                    .background(Color(0xFFE0E0E0).copy(alpha = 0.2f), shape = CircleShape)
            )
            // Small circle center-right
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 300.dp, y = 200.dp)
                    .background(Color(0xFFE0E0E0).copy(alpha = 0.1f), shape = CircleShape)
            )
            // Organic shape top-right
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 320.dp, y = (-20).dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(Color(0xFFE0E0E0).copy(alpha = 0.15f))
            )
        }

        // Main content (matching LoginScreen structure)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(0.2f))

            // Title (positioned like "Enter phone or email" in previous screen)
            val registrationMethod = when (uiState.selectedOption) {
                "Email" -> "with ${uiState.email}"
                "Phone" -> "with ${uiState.phoneNumber}"
                else -> ""
            }
            Text(
                text = "Next, create an account $registrationMethod",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp, top = 24.dp)
            )

            // Name field
            TextInputField(
                value = uiState.name,
                onValueChange = { viewModel.onEvent(RegisterEvent.EnteredName(it)) },
                label = "Name",
                placeholder = "Enter your name",
                error = uiState.nameError
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Password field
            PasswordInputField(
                value = uiState.password,
                onValueChange = { viewModel.onEvent(RegisterEvent.EnteredPassword(it)) },
                label = "Password",
                placeholder = "Create a password",
                error = uiState.passwordError
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Create Account Button
            Button(
                onClick = {
                    viewModel.onEvent(RegisterEvent.Register)
                },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB0B3FF),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Create Account",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.weight(0.5f))
        }

        // Top navigation row (matching LoginScreen)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back arrow button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Centered title
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center
            )

            // Empty space for balance (same width as back button)
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}
