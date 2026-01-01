package com.socialnetwork.checking_sn.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.core.presentation.util.Screen
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
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {}
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Glassmorphic background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .blur(20.dp)
                .background(Color.White.copy(alpha = 0.2f))
        )

        // Navigation icons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val fabSize = 56.dp
            val navItemsChunked = navItems.chunked(navItems.size / 2)

            // Left side icons
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                navItemsChunked[0].forEach { item ->
                    val isSelected = currentRoute == item.route
                    IconButton(onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = if (isSelected) item.filledIcon else item.icon),
                            contentDescription = item.label,
                            tint = if (isSelected) MindesPurple else Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            // Spacer for the FAB in the middle
            Spacer(modifier = Modifier.width(fabSize))

            // Right side icons
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                navItemsChunked[1].forEach { item ->
                    val isSelected = currentRoute == item.route
                    IconButton(onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = if (isSelected) item.filledIcon else item.icon),
                            contentDescription = item.label,
                            tint = if (isSelected) MindesPurple else Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        // Floating "+" button
        val fabSize = 56.dp
        Box(
            modifier = Modifier
                .size(fabSize)
                .align(Alignment.TopCenter)
                .offset(y = -fabSize / 4) // Adjust the offset to your liking
                .background(MindesPurple, shape = CircleShape)
                .clickable {
                    navController.navigate(Screen.CreatePostScreen.route)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Post",
                tint = Color.White
            )
        }
    }
}
