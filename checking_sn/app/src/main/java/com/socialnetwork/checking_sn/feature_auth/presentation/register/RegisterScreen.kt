package com.socialnetwork.checking_sn.feature_auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.socialnetwork.checking_sn.core.presentation.components.*
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.ui.components.TopBar
import com.socialnetwork.checking_sn.ui.theme.LightGrayShapes
import com.socialnetwork.checking_sn.ui.theme.Spacing
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateToRegisterDetails -> {
                    val state = viewModel.uiState.value
                    val (type, value) = when (state.selectedOption) {
                        "Email" -> "email" to state.email
                        "Phone" -> "phone" to state.phoneNumber
                        else -> "email" to state.email
                    }
                    navController.navigate(Screen.RegisterDetailsScreen.createRoute(type, value))
                }
                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Sign Up",
                onBackClick = { navController.popBackStack() },
                topPadding = Spacing.Medium
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Background shapes (matching LoginScreen)
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

            // Title
            Text(
                text = "Enter phone or email",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(bottom = Spacing.Large)
            )

            // Segmented Control
            SegmentedToggle(
                selectedOption = uiState.selectedOption,
                options = listOf("Email", "Phone"),
                onOptionSelected = { viewModel.onEvent(RegisterEvent.SelectedOption(it)) },
                modifier = Modifier.padding(bottom = Spacing.ExtraLarge)
            )

            // Input Field
            when (uiState.selectedOption) {
                "Email" -> {
                    TextInputField(
                        value = uiState.email,
                        onValueChange = { viewModel.onEvent(RegisterEvent.EnteredEmail(it)) },
                        label = "Email",
                        placeholder = "Enter your email",
                        error = uiState.emailError,
                        keyboardType = KeyboardType.Email
                    )
                }
                "Phone" -> {
                    Column {
                        CountryCodeSelector(
                            selectedCountryCode = uiState.countryCode,
                            selectedCountryIsoCode = uiState.countryIsoCode,
                            onCountrySelected = { code, iso ->
                                viewModel.onEvent(RegisterEvent.SelectedCountry(code, iso))
                            }
                        )
                        TextInputField(
                            value = uiState.phoneNumber,
                            onValueChange = { viewModel.onEvent(RegisterEvent.EnteredPhoneNumber(it)) },
                            label = "Phone Number",
                            placeholder = "Enter your phone number",
                            error = uiState.phoneNumberError,
                            keyboardType = KeyboardType.Phone
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Spacing.ExtraLarge))

            // Next Button
            PrimaryButton(
                text = "Next",
                onClick = { viewModel.onEvent(RegisterEvent.Register) }
            )

            Spacer(modifier = Modifier.weight(0.5f))
        }

        }
    }
}
