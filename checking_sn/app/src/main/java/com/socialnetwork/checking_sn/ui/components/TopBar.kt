package com.socialnetwork.checking_sn.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.ui.theme.Spacing

@Composable
fun TopBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    topPadding: androidx.compose.ui.unit.Dp = 0.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = Spacing.ScreenPaddingHorizontal,
                vertical = Spacing.ScreenPaddingVertical
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Back arrow button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(Spacing.TopBarHeight)
                .padding(top = topPadding)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(Spacing.IconSizeSmall)
            )
        }

        // Centered title
        Text(
            text = title,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.padding(top = topPadding)
        )

        // Empty space for balance (same width as back button)
        Spacer(modifier = Modifier.size(Spacing.TopBarHeight))
    }
}
