package com.socialnetwork.checking_sn.feature_auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.components.StandardTextField
import com.socialnetwork.checking_sn.core.presentation.components.auth.OrDivider
import com.socialnetwork.checking_sn.core.presentation.components.auth.SocialLoginButton
import com.socialnetwork.checking_sn.core.presentation.components.buttons.PrimaryActionButton
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.FEED_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.UiEvent
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import com.socialnetwork.checking_sn.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

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

    Box(modifier = Modifier.fillMaxSize()) {
        // Blurred background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            BackgroundGradientStart,
                            BackgroundGradientMiddle,
                            BackgroundGradientEnd
                        )
                    )
                )
                .blur(20.dp)
        )

        // Main card
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 80.dp)
                .shadow(
                    elevation = 32.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Black.copy(alpha = 0.2f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                ),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { focusManager.clearFocus() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App logo at top
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp).size(300.dp, 150.dp)
                )

                // Centered main form
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Email field
                        StandardTextField(
                            text = viewModel.emailState.value.text,
                            onValueChange = {
                                viewModel.onEvent(LoginEvent.EnteredEmail(it))
                            },
                            error = viewModel.emailState.value.error,
                            hint = stringResource(id = R.string.login_hint),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = Color.Gray
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Sign In button
                        PrimaryActionButton(
                            text = stringResource(id = R.string.sign_in),
                            onClick = {
                                viewModel.onEvent(LoginEvent.Login)
                            },
                            enabled = !viewModel.loginState.value.isLoading,
                            isAuthStyle = true
                        )

                        // Divider
                        OrDivider(modifier = Modifier.padding(top = 32.dp))

                        Spacer(modifier = Modifier.height(32.dp))

                        // Social logins
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Google button
                            SocialLoginButton(onClick = { /* TODO: Google login */ })
                        }

                        // Secondary text at bottom
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Create yours now",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF7C3AED),
                                modifier = Modifier
                                    .padding(bottom = 32.dp)
                                    .clickable {
                                        navController.navigate(Screen.RegisterScreen.route)
                                    }
                            )
                        }
                    }
                }


            }
        }

        // Footer
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.privacy),
                style = MaterialTheme.typography.bodySmall,
                color = FooterTextColor
            )
            Text(
                text = stringResource(id = R.string.terms),
                style = MaterialTheme.typography.bodySmall,
                color = FooterTextColor
            )
            Text(
                text = stringResource(id = R.string.help),
                style = MaterialTheme.typography.bodySmall,
                color = FooterTextColor
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}
