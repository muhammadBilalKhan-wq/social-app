package com.socialnetwork.checking_sn.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// Additional custom shapes for specific use cases
val ButtonShape = RoundedCornerShape(28.dp)
val InputFieldShape = RoundedCornerShape(12.dp)
val CardShape = RoundedCornerShape(16.dp)
val TopBarShape = RoundedCornerShape(0.dp) // No rounding for top bars
val ModalShape = RoundedCornerShape(16.dp)
val SegmentedControlShape = RoundedCornerShape(25.dp)
