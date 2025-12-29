package com.socialnetwork.checking_sn.feature_auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.core.presentation.components.PasswordInputField
import com.socialnetwork.checking_sn.core.presentation.components.PrimaryButton
import com.socialnetwork.checking_sn.core.presentation.components.TextInputField
import com.socialnetwork.checking_sn.ui.components.TopBar
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.ui.theme.LightGrayShapes
import com.socialnetwork.checking_sn.ui.theme.LinkTextColor
import com.socialnetwork.checking_sn.ui.theme.Spacing
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.OnLogin -> {
                    navController.navigate(FEED_GRAPH_ROUTE) {
                        popUpTo(AUTH_GRAPH_ROUTE) { inclusive = true }
                    }
                }
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.uiText.asString(context))
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBar(
                title = "Login",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Background translucent shapes
            Box(modifier = Modifier.fillMaxSize()) {
                // Large circle top-left
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .offset(x = (-50).dp, y = (-50).dp)
                        .background(LightGrayShapes, shape = CircleShape)
                )
                // Medium circle bottom-right
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .offset(x = 250.dp, y = 400.dp)
                        .background(LightGrayShapes, shape = CircleShape)
                )
                // Small circle center-right
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .offset(x = 300.dp, y = 200.dp)
                        .background(LightGrayShapes, shape = CircleShape)
                )
                // Organic shape top-right
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .offset(x = 320.dp, y = (-20).dp)
                        .clip(CircleShape)
                        .background(LightGrayShapes)
                )
            }

            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.ScreenPaddingHorizontal, vertical = Spacing.ScreenPaddingVertical)
                    .verticalScroll(rememberScrollState())
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(Spacing.ExtraLarge))

                // Header
                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = Spacing.XXLarge)
                )

                // Email field
                TextInputField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                    label = "Email or Phone Number",
                    placeholder = "Enter your email or phone",
                    error = uiState.emailError,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(Spacing.Medium))

                // Password field
                PasswordInputField(
                    value = uiState.password,
                    onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                    label = "Password",
                    placeholder = "Enter your password",
                    error = uiState.passwordError
                )

                Spacer(modifier = Modifier.height(Spacing.ExtraLarge))

                // Log In button
                PrimaryButton(
                    text = "Log In",
                    onClick = { viewModel.onEvent(LoginEvent.Login) },
                    enabled = !uiState.isLoading
                )

                // Forgot Password link
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = LinkTextColor,
                    modifier = Modifier
                        .padding(top = Spacing.Large)
                        .clickable { /* TODO: Navigate to forgot password */ }
                )

                Spacer(modifier = Modifier.height(Spacing.ExtraLarge))
            }
        }
    }
}
