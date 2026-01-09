package com.socialnetwork.checking_sn.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.socialnetwork.checking_sn.R
import com.socialnetwork.checking_sn.ui.theme.AppBackgroundColor
import com.socialnetwork.checking_sn.ui.theme.Spacing

// Reusable top bar icon matching bottom nav style
@Composable
fun TopBarIcon(
    icon: @Composable () -> androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(40.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
        color = Color.Transparent
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon(),
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TopNavigationBar(
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current

    DisposableEffect(Unit) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            // Make status bar icons dark for light background
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = true
        }
        onDispose {}
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = AppBackgroundColor,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Spacing.TopBarHeight)
                .padding(horizontal = Spacing.Medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Left logo
            Image(
                painter = painterResource(id = R.drawable.app_logo_notext),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(Spacing.TopBarHeight)
                    .padding(start = Spacing.Small)
            )

            // Spacer to push icons to the right
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

            // Right icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
            ) {
                TopBarIcon(
                    icon = { Icons.Filled.Search },
                    contentDescription = "Search",
                    onClick = onSearchClick
                )
                TopBarIcon(
                    icon = { Icons.Outlined.Person },
                    contentDescription = "Profile",
                    onClick = onProfileClick
                )
            }
        }
    }
}
