package com.socialnetwork.checking_sn.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

// Home icon as ImageVector
@Composable
fun homeIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // House outline
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
                moveTo(3f, 9f)
                lineTo(12f, 2f)
                lineTo(21f, 9f)
                verticalLineTo(20f)
                arcTo(1f, 1f, 0f, false, true, 20f, 21f)
                horizontalLineTo(4f)
                arcTo(1f, 1f, 0f, false, true, 3f, 20f)
                close()
            }
            // Door
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
                moveTo(9f, 21f)
                verticalLineTo(11f)
                horizontalLineTo(15f)
                verticalLineTo(21f)
            }
        }.build()
    }
}

// Shorts icon - play triangle in circle
@Composable
fun shortsIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "Shorts",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Circle background
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
                moveTo(12f, 2f)
                arcTo(10f, 10f, 0f, true, false, 12f, 22f)
                arcTo(10f, 10f, 0f, false, false, 12f, 2f)
                close()
            }
            // Play triangle - centered
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
                moveTo(10f, 9f)
                lineTo(10f, 15f)
                lineTo(16f, 12f)
                close()
            }
        }.build()
    }
}

// Notifications icon
@Composable
fun NotificationsIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "Notifications",
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
                moveTo(18f, 8f)
                arcTo(6f, 6f, 0f, false, false, 6f, 8f)
                curveTo(6f, 14f, 3f, 16f, 3f, 16f)
                horizontalLineTo(21f)
                reflectiveCurveTo(18f, 14f, 18f, 8f)
                close()
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
                moveTo(13.73f, 21f)
                arcTo(2f, 2f, 0f, false, true, 10.27f, 21f)
            }
        }.build()
    }
}

// More icon - 3×3 grid of simple dots
@Composable
fun MoreIcon(): ImageVector {
    val color = Color(0xFF7C3AED)
    return remember(color) {
        ImageVector.Builder(
            name = "More",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Simple dots using small filled circles
            // Top row
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(6f, 6f)
                lineTo(8f, 6f)
                lineTo(8f, 8f)
                lineTo(6f, 8f)
                close()
            }
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 6f)
                lineTo(13f, 6f)
                lineTo(13f, 8f)
                lineTo(11f, 8f)
                close()
            }
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(16f, 6f)
                lineTo(18f, 6f)
                lineTo(18f, 8f)
                lineTo(16f, 8f)
                close()
            }
            // Middle row
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(6f, 11f)
                lineTo(8f, 11f)
                lineTo(8f, 13f)
                lineTo(6f, 13f)
                close()
            }
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 11f)
                lineTo(13f, 11f)
                lineTo(13f, 13f)
                lineTo(11f, 13f)
                close()
            }
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(16f, 11f)
                lineTo(18f, 11f)
                lineTo(18f, 13f)
                lineTo(16f, 13f)
                close()
            }
            // Bottom row
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(6f, 16f)
                lineTo(8f, 16f)
                lineTo(8f, 18f)
                lineTo(6f, 18f)
                close()
            }
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 16f)
                lineTo(13f, 16f)
                lineTo(13f, 18f)
                lineTo(11f, 18f)
                close()
            }
            path(fill = SolidColor(color), pathFillType = PathFillType.NonZero) {
                moveTo(16f, 16f)
                lineTo(18f, 16f)
                lineTo(18f, 18f)
                lineTo(16f, 18f)
                close()
            }
        }.build()
    }
}

// Add/Post icon
@Composable
fun AddIcon(): ImageVector {
    val color = Color.White
    return remember(color) {
        ImageVector.Builder(
            name = "Add",
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
                moveTo(12f, 5f)
                verticalLineTo(19f)
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
                moveTo(5f, 12f)
                horizontalLineTo(19f)
            }
        }.build()
    }
}

// Reusable navigation icon with active state
@Composable
fun NavIcon(
    icon: @Composable () -> ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColor = Color(0xFF7C3AED) // Purple color for active state

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) {
            activeColor
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(durationMillis = 300),
        label = "iconColor"
    )

    Surface(
        modifier = modifier.size(40.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
        color = Color.Transparent
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon(),
                contentDescription = contentDescription,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Reusable add/post button with press interaction
@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scale"
    )

    Surface(
        modifier = modifier
            .size(56.dp)
            .scale(scale),
        color = Color.White,
        shape = androidx.compose.foundation.shape.CircleShape,
        shadowElevation = 4.dp
    ) {
        IconButton(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = AddIcon(),
                contentDescription = "Add",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Glass background container for navigation icons
@Composable
fun GlassNavigationContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 72.dp)
            .height(56.dp),
        color = Color.White,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: String? = null,
    onHomeClick: () -> Unit = {},
    onShortsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val navigationBarColor = Color(0xFFF5F5F7) // Light gray color for system navigation bar

    DisposableEffect(Unit) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            val originalColor = it.navigationBarColor
            it.navigationBarColor = android.graphics.Color.argb(
                (navigationBarColor.alpha * 255).toInt(),
                (navigationBarColor.red * 255).toInt(),
                (navigationBarColor.green * 255).toInt(),
                (navigationBarColor.blue * 255).toInt()
            )
            // Make navigation bar icons dark for light background
            WindowCompat.getInsetsController(it, view).isAppearanceLightNavigationBars = true

            onDispose {
                it.navigationBarColor = originalColor
            }
        }
        onDispose {}
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
    ) {
        GlassNavigationContainer(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            NavIcon(
                icon = { homeIcon() },
                contentDescription = "Home",
                isSelected = selectedTab == "home",
                onClick = onHomeClick
            )
            NavIcon(
                icon = { shortsIcon() },
                contentDescription = "Shorts",
                isSelected = selectedTab == "shorts",
                onClick = onShortsClick
            )
            NavIcon(
                icon = { NotificationsIcon() },
                contentDescription = "Notifications",
                isSelected = selectedTab == "notifications",
                onClick = onNotificationsClick
            )
            NavIcon(
                icon = { MoreIcon() },
                contentDescription = "More",
                isSelected = selectedTab == "more",
                onClick = onMoreClick
            )
        }

        // Add/Post button
        AddButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onAddClick
        )
    }
}
