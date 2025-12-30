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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.core.presentation.components.PasswordInputField
import com.socialnetwork.checking_sn.core.presentation.components.PrimaryButton
import com.socialnetwork.checking_sn.core.presentation.components.SegmentedToggle
import com.socialnetwork.checking_sn.core.presentation.components.TextInputField
import com.socialnetwork.checking_sn.core.presentation.components.CountryCodeSelector
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
                .statusBarsPadding()
                .imePadding()
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

            // Single scrollable column with keyboard awareness
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.ScreenPaddingHorizontal, vertical = Spacing.ScreenPaddingVertical)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Normal top spacing
                Spacer(modifier = Modifier.height(Spacing.ExtraLarge))

                // Additional spacing to move contents below
                Spacer(modifier = Modifier.height(Spacing.ExtraLarge))

                // Segmented Control
                SegmentedToggle(
                    selectedOption = uiState.selectedOption,
                    options = listOf("Email", "Phone"),
                    onOptionSelected = { viewModel.onEvent(LoginEvent.SelectedOption(it)) },
                    modifier = Modifier.padding(bottom = Spacing.ExtraLarge)
                )

                // Input Field
                val currentError = when (uiState.selectedOption) {
                    "Email" -> uiState.emailError
                    "Phone" -> uiState.phoneNumberError
                    else -> uiState.emailError
                }

                when (uiState.selectedOption) {
                    "Email" -> {
                        TextInputField(
                            value = uiState.email,
                            onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                            label = "Email",
                            placeholder = "Enter your email",
                            error = currentError,
                            keyboardType = KeyboardType.Email
                        )
                    }
                    "Phone" -> {
                        TextInputField(
                            value = uiState.phoneNumber,
                            onValueChange = { viewModel.onEvent(LoginEvent.EnteredPhoneNumber(it)) },
                            label = "Phone Number",
                            placeholder = "Enter phone number without country code",
                            error = currentError,
                            keyboardType = KeyboardType.Phone,
                            inputFilter = { input ->
                                // Allow only digits and spaces, but prevent country code patterns and limit length
                                val filtered = input.filter { it.isDigit() || it.isWhitespace() }
                                // Don't allow starting with + or common country code patterns
                                if (filtered.startsWith("+") || filtered.matches(Regex("^\\d{1,3}[+\\s].*"))) {
                                    "" // Reject input that looks like country codes
                                } else {
                                    // Limit to 12 digits max
                                    val digitsOnly = filtered.filter { it.isDigit() }
                                    if (digitsOnly.length > 12) {
                                        filtered.dropLast(1) // Remove the last character if too many digits
                                    } else {
                                        filtered
                                    }
                                }
                            },
                            leadingIcon = {
                                CountryCodeSelector(
                                    selectedCountryCode = uiState.countryCode,
                                    selectedCountryIsoCode = uiState.countryIsoCode,
                                    onCountrySelected = { code, iso ->
                                        viewModel.onEvent(LoginEvent.SelectedCountry(code, iso))
                                    },
                                    compact = true
                                )
                            }
                        )
                    }
                }

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

                // Log In button (in normal position after password field)
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

                // Extra bottom spacing for scrolling comfort
                Spacer(modifier = Modifier.height(Spacing.ExtraLarge))
            }
        }
    }
}
