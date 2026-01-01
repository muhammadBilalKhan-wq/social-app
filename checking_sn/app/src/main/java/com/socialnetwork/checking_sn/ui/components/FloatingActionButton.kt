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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    Surface(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .clickable {
                navController.navigate(Screen.CreateContentScreen.route) {
                    launchSingleTop = true
                }
            },
        shape = CircleShape,
        shadowElevation = 4.dp,
        color = Color.White.copy(alpha = 0.15f)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add",
                tint = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
