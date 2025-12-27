package com.socialnetwork.checking_sn.core.presentation.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isAuthStyle: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .then(if (isAuthStyle) Modifier.fillMaxWidth().height(50.dp) else Modifier)
            .then(if (!isAuthStyle) Modifier.fillMaxWidth() else Modifier),
        shape = if (isAuthStyle) RoundedCornerShape(25.dp) else RoundedCornerShape(4.dp),
        colors = if (isAuthStyle) {
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C3AED),
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors()
        },
        enabled = enabled
    ) {
        Text(
            text = text,
            style = if (isAuthStyle) {
                TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            } else {
                MaterialTheme.typography.labelLarge
            }
        )
    }
}
