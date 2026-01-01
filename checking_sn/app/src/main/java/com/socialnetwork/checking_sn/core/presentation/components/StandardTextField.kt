package com.socialnetwork.checking_sn.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.ui.theme.InputBackground
import com.socialnetwork.checking_sn.ui.theme.LabelTextColor

// Custom visibility icon
@Composable
private fun visibilityIcon(): ImageVector {
    val color = MaterialTheme.colorScheme.onSurface
    return androidx.compose.runtime.remember(color) {
        ImageVector.Builder(
            name = "Visibility",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(1f, 12f)
                reflectiveCurveTo(5f, 4f, 12f, 4f)
                reflectiveCurveTo(23f, 12f, 23f, 12f)
                reflectiveCurveTo(19f, 20f, 12f, 20f)
                reflectiveCurveTo(1f, 12f, 1f, 12f)
                close()
            }
            path(
                fill = SolidColor(color),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 9f)
                arcTo(3f, 3f, 0f, true, false, 12f, 15f)
                arcTo(3f, 3f, 0f, false, false, 12f, 9f)
                close()
            }
        }.build()
    }
}

// Custom visibility off icon
@Composable
private fun visibilityOffIcon(): ImageVector {
    val color = MaterialTheme.colorScheme.onSurface
    return androidx.compose.runtime.remember(color) {
        ImageVector.Builder(
            name = "VisibilityOff",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9.9f, 4.24f)
                arcToRelative(9.12f, 9.12f, 0f, false, true, 12f, 4f)
                curveTo(5f, 20f, 1f, 12f, 1f, 12f)
                reflectiveCurveTo(18f, 20f, 12f, 20f)
                arcToRelative(10.07f, 10.07f, 0f, false, true, -2.1f, -0.24f)
            }
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9.9f, 4.24f)
                arcToRelative(9.12f, 9.12f, 0f, false, true, 12f, 4f)
                curveToRelative(7f, 0f, 11f, 8f, 11f, 8f)
                reflectiveCurveTo(18f, 20f, 12f, 20f)
                arcToRelative(10.07f, 10.07f, 0f, false, true, -2.1f, -0.24f)
            }
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(color),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(1f, 1f)
                lineTo(23f, 23f)
            }
        }.build()
    }
}

@Composable
fun StandardTextField(
    text: String = "",
    label: String = "",
    hint: String = "",
    error: UiText? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    showPasswordToggle: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = LabelTextColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        OutlinedTextField(
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )
            },
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (showPasswordToggle) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (keyboardType == KeyboardType.Password) {
                    IconButton(onClick = {
                        onPasswordToggleClick(!showPasswordToggle)
                    }) {
                        Icon(
                            imageVector = if (showPasswordToggle) {
                                visibilityOffIcon()
                            } else {
                                visibilityIcon()
                            },
                            contentDescription = null
                        )
                    }
                }
            },
            maxLines = maxLines,
            shape = RoundedCornerShape(50.dp), // Fully rounded
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = InputBackground,
                unfocusedContainerColor = InputBackground
            ),
            textStyle = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 17.sp, fontWeight = FontWeight.Normal, color = Color(0xFF1D1D1F)),
            modifier = modifier.fillMaxWidth()
        )
        if (error != null) {
            Text(
                text = error.asString(context),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }

}
