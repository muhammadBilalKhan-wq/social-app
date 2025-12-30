package com.socialnetwork.checking_sn.ui.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.util.Screen
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SweepGradientShader
import com.socialnetwork.checking_sn.ui.theme.MindesPurple

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val filledIcon: Int,
    val label: String
)

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navItems = listOf(
        BottomNavItem(
            route = Screen.HomeScreen.route,
            icon = R.drawable.ic_home_outlined,
            filledIcon = R.drawable.ic_home_filled,
            label = "Home"
        ),
        BottomNavItem(
            route = Screen.ShortsScreen.route,
            icon = R.drawable.ic_shorts_outlined,
            filledIcon = R.drawable.ic_shorts_filled,
            label = "Shorts"
        ),
        BottomNavItem(
            route = Screen.NotificationsScreen.route,
            icon = R.drawable.ic_notifications_outlined,
            filledIcon = R.drawable.ic_notifications_filled,
            label = "Notifications"
        ),
        BottomNavItem(
            route = Screen.MoreScreen.route,
            icon = R.drawable.ic_more_outlined,
            filledIcon = R.drawable.ic_more_filled,
            label = "More"
        )
    )

    val glassmorphismModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.graphicsLayer(
            renderEffect = RenderEffect.createBlurEffect(
                8f, 8f, android.graphics.Shader.TileMode.MIRROR
            ).asComposeRenderEffect()
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(28.dp))
            .then(glassmorphismModifier)
            .background(Color.White.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = currentRoute == item.route
                val icon = if (isSelected) item.filledIcon else item.icon
                val iconTint = if (isSelected) MindesPurple else Color.Gray

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = item.label,
                        tint = iconTint,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}