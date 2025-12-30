package com.socialnetwork.checking_sn.core.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.ui.theme.SegmentedControlShape
import com.socialnetwork.checking_sn.ui.theme.Spacing

@Composable
fun SegmentedToggle(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(Spacing.TopBarHeight)
            .clip(SegmentedControlShape)
            .background(Color.White)
            .border(1.dp, Color.Black.copy(alpha = 0.3f), SegmentedControlShape)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color(0xFF666666),
                label = "textColor"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) { onOptionSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                // Selection indicator background
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF333333))
                    )
                }

                Text(
                    text = option,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor
                )
            }
        }
    }
}
