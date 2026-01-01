package com.socialnetwork.checking_sn.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.socialnetwork.checking_sn.core.util.UiText
import com.socialnetwork.checking_sn.ui.theme.InputBackground
import com.socialnetwork.checking_sn.ui.theme.InputFieldShape
import com.socialnetwork.checking_sn.ui.theme.LabelTextColor
import com.socialnetwork.checking_sn.ui.theme.Spacing

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
                moveTo(17.94f, 17.94f)
                arcTo(10.07f, 10.07f, 0f, false, true, 12f, 20f)
                curveTo(5f, 20f, 1f, 12f, 1f, 12f)
                reflectiveCurveTo(5f, 4f, 12f, 4f)
                arcToRelative(10.07f, 10.07f, 0f, false, true, 5.94f, 2.06f)
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
                arcTo(9.12f, 9.12f, 0f, false, true, 12f, 4f)
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
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String = "",
    error: UiText? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showPassword by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = LabelTextColor,
                modifier = Modifier.padding(bottom = Spacing.ExtraSmall)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )
            },
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.None
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) visibilityOffIcon() else visibilityIcon(),
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            },
            shape = InputFieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = InputBackground,
                unfocusedContainerColor = InputBackground,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(Spacing.InputFieldHeight)
        )

        if (error != null) {
            Text(
                text = error.asString(context),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(top = Spacing.ExtraSmall)
            )
        }
    }
}
