package com.socialnetwork.checking_sn.feature_splash.presentation.splash

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.util.AUTH_GRAPH_ROUTE
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    // Animated gradient background
    val colors1 = listOf(
        Color(0xFF7C3AED).copy(alpha = 0.5f),
        Color(0xFFD1C4E9).copy(alpha = 0.6f)
    )
    val colors2 = listOf(
        Color(0xFFE8E8E8).copy(alpha = 0.4f),
        Color(0xFFF3F3F3).copy(alpha = 0.5f)
    )
    val colors3 = listOf(
        Color(0xFF7C3AED).copy(alpha = 0.3f),
        Color(0xFFD1C4E9).copy(alpha = 0.4f)
    )

    var index by remember { mutableStateOf(0) }

    val color1 by animateColorAsState(
        targetValue = colors1[index % colors1.size],
        animationSpec = tween(4000)
    )
    val color2 by animateColorAsState(
        targetValue = colors2[index % colors2.size],
        animationSpec = tween(4000)
    )
    val color3 by animateColorAsState(
        targetValue = colors3[index % colors3.size],
        animationSpec = tween(4000)
    )

    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(16)
            time += 0.016f
        }
    }

    val gradientBrush = Brush.radialGradient(
        colors = listOf(color1, color2, color3),
        center = Offset(0.5f + sin(time * 0.01f) * 0.3f, 0.5f + cos(time * 0.01f) * 0.3f),
        radius = 1f
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            index = (index + 1) % 2
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {

        // Logo with tagline overlaid on top
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-118).dp), // Position slightly above center
            contentAlignment = Alignment.Center
        ) {
            // Logo as background
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(360.dp, 180.dp)
            )

            // Tagline overlaid on top of logo
            Text(
                text = "LEARN · THINK · SHARE",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF5D4D5D)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(y = 65.dp) // Position below center of logo
            )
        }

        // Buttons positioned lower, separate from logo/tagline group
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 58.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Login button - secondary/neutral
            Button(
                onClick = {
                    navController.navigate(AUTH_GRAPH_ROUTE)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(46.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = Color.Black.copy(alpha = 0.05f),
                        spotColor = Color.Black.copy(alpha = 0.05f)
                    ),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF666666)
                ),
                border = BorderStroke(1.dp, Color(0xFF666666))
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // Sign Up button - primary with brand color
            Button(
                onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(46.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = Color(0xFF7C3AED).copy(alpha = 0.3f),
                        spotColor = Color(0xFF7C3AED).copy(alpha = 0.3f)
                    ),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C3AED), // Brand color
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
