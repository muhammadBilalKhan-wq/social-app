package com.socialnetwork.checking_sn.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.socialnetwork.checking_sn.ui.theme.ButtonShape
import com.socialnetwork.checking_sn.ui.theme.SoftPeriwinkleBlue
import com.socialnetwork.checking_sn.ui.theme.Spacing

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Spacing.ButtonHeight),
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = SoftPeriwinkleBlue,
            contentColor = Color.White,
            disabledContainerColor = SoftPeriwinkleBlue.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.5f)
        ),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )
    }
}
