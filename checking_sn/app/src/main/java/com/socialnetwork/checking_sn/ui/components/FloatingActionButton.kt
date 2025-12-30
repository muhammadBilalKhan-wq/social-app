package com.socialnetwork.checking_sn.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.util.Screen

@Composable
fun FloatingAddButton(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .size(56.dp)
            .graphicsLayer {
                // Glassmorphism blur effect removed for compatibility
                // Blur effects require Compose UI 1.4.0+ and Android 12+
            }
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f), // Very low opacity white border
                shape = CircleShape
            )
            .clickable {
                // Navigate to create content screen
                navController.navigate(Screen.CreateContentScreen.route) {
                    launchSingleTop = true
                }
            },
        shape = CircleShape,
        shadowElevation = 4.dp, // Reduced elevation for softer shadow
        color = Color.White.copy(alpha = 0.15f) // Semi-transparent white background
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent) // No background fill - glass effect
                .size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add",
                tint = Color.White.copy(alpha = 0.9f), // Slightly transparent white for glass effect
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
