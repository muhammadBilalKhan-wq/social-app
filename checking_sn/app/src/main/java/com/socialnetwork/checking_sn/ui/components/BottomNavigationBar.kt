package com.socialnetwork.checking_sn.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import android.graphics.RenderEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Shader
import android.os.Build
import com.socialnetwork.checking_sn.ui.theme.AppBackgroundColor

private fun BlurEffect(radiusX: Float, radiusY: Float, edgeTreatment: Shader.TileMode): RenderEffect? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        RenderEffect.createBlurEffect(radiusX, radiusY, edgeTreatment)
    } else null
}

// Home icon as ImageVector
@Composable
fun homeIcon(): ImageVector {
    return remember {
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
                stroke = SolidColor(Color.Black),
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
                lineTo(21f, 20f)
                lineTo(20f, 21f)
                lineTo(4f, 21f)
                lineTo(3f, 20f)
                close()
            }
            // Door
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9f, 21f)
                lineTo(9f, 11f)
                lineTo(15f, 11f)
                lineTo(15f, 21f)
            }
        }.build()
    }
}

// Standard navigation icon in pill shape
@Composable
fun StandardNavIcon(
    icon: ImageVector,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = Color.Transparent,
        modifier = modifier
            .size(48.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(24.dp)
                .padding(12.dp)
        )
    }
}

// Plus icon - separate circular container, more prominent
@Composable
fun PlusIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scale"
    )

    Surface(
        shape = CircleShape,
        color = AppBackgroundColor.copy(alpha = 1.0f),
        shadowElevation = 24.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = modifier
            .size(56.dp)
            .scale(scale)
            .shadow(
                elevation = 16.dp,
                shape = CircleShape,
                ambientColor = Color.White.copy(alpha = 0.3f),
                spotColor = Color.White.copy(alpha = 0.3f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        val iconTint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        Icon(
            imageVector = AddIcon(iconTint),
            contentDescription = "Add",
            modifier = Modifier
                .size(24.dp)
                .padding(16.dp)
        )
    }
}

// Shorts icon - play triangle
@Composable
fun shortsIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "Shorts",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Play triangle - centered
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(8f, 5f)
                lineTo(8f, 19f)
                lineTo(19f, 12f)
                close()
            }
        }.build()
    }
}

// Notifications icon - bell
@Composable
fun NotificationsIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "Notifications",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Bell body
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(18f, 8f)
                lineTo(6f, 8f)
                lineTo(3f, 16f)
                lineTo(21f, 16f)
                close()
            }
            // Bell clapper
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13.73f, 21f)
                lineTo(10.27f, 21f)
            }
            // Bell handle
            path(
                fill = SolidColor(Color.Transparent),
                fillAlpha = 1f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 1f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12f, 2f)
                lineTo(12f, 4f)
            }
        }.build()
    }
}

// More icon - 3×3 grid of simple dots
@Composable
fun MoreIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "More",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Simple dots using small filled circles
            // Top row
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(6f, 6f)
                lineTo(8f, 6f)
                lineTo(8f, 8f)
                lineTo(6f, 8f)
                close()
            }
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 6f)
                lineTo(13f, 6f)
                lineTo(13f, 8f)
                lineTo(11f, 8f)
                close()
            }
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(16f, 6f)
                lineTo(18f, 6f)
                lineTo(18f, 8f)
                lineTo(16f, 8f)
                close()
            }
            // Middle row
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(6f, 11f)
                lineTo(8f, 11f)
                lineTo(8f, 13f)
                lineTo(6f, 13f)
                close()
            }
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 11f)
                lineTo(13f, 11f)
                lineTo(13f, 13f)
                lineTo(11f, 13f)
                close()
            }
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(16f, 11f)
                lineTo(18f, 11f)
                lineTo(18f, 13f)
                lineTo(16f, 13f)
                close()
            }
            // Bottom row
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(6f, 16f)
                lineTo(8f, 16f)
                lineTo(8f, 18f)
                lineTo(6f, 18f)
                close()
            }
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
                moveTo(11f, 16f)
                lineTo(13f, 16f)
                lineTo(13f, 18f)
                lineTo(11f, 18f)
                close()
            }
            path(fill = SolidColor(Color.Black), pathFillType = PathFillType.NonZero) {
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
fun AddIcon(tintColor: Color = Color.White): ImageVector {
    return remember(tintColor) {
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
                stroke = SolidColor(tintColor),
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
                stroke = SolidColor(tintColor),
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

    // Detect navigation mode: gesture navigation (nav bar hidden, bottom inset == 0) allows overlay transparency
    // 3-button navigation (nav bar visible, bottom inset > 0) requires placing icons above, accepts system background
    val isGestureNavigation = remember(view) {
        val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets)
        insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom == 0
    }

    DisposableEffect(Unit) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            // Set navigation bar to match app background with glass effect
            it.navigationBarColor = AppBackgroundColor.copy(alpha = 0.95f).toArgb()
            val insetsController = WindowCompat.getInsetsController(it, view)
            // Make navigation bar icons dark for light background
            insetsController.isAppearanceLightNavigationBars = true

            onDispose {
                // No need to restore
            }
        }
        onDispose {}
    }

    val blurEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        try {
            RenderEffect.createBlurEffect(12f, 12f, Shader.TileMode.CLAMP) as androidx.compose.ui.graphics.RenderEffect?
        } catch (e: Exception) {
            null
        }
    } else null

    SubcomposeLayout(
        modifier = modifier
            .then(if (!isGestureNavigation) Modifier.navigationBarsPadding() else Modifier)
    ) { constraints ->
        // Subcompose the nav UI to measure its size
        val navMeasurable = subcompose("nav") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Four navigation icons in their own rounded container
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.LightGray.copy(alpha = 0.9f),

                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Home
                        StandardNavIcon(
                            icon = homeIcon(),
                            contentDescription = "Home",
                            isSelected = selectedTab == "home",
                            onClick = onHomeClick
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Shorts
                        StandardNavIcon(
                            icon = shortsIcon(),
                            contentDescription = "Shorts",
                            isSelected = selectedTab == "shorts",
                            onClick = onShortsClick
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Notifications
                        StandardNavIcon(
                            icon = NotificationsIcon(),
                            contentDescription = "Notifications",
                            isSelected = selectedTab == "notifications",
                            onClick = onNotificationsClick
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // More
                        StandardNavIcon(
                            icon = MoreIcon(),
                            contentDescription = "More",
                            isSelected = selectedTab == "more",
                            onClick = onMoreClick
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Plus icon - separate with its own fully rounded container
                PlusIcon(onClick = onAddClick)
            }
        }

        val navPlaceable = navMeasurable.first().measure(constraints)
        val navHeight = navPlaceable.height

        // Layout with full width and nav height
        layout(constraints.maxWidth, navHeight) {
            // Place the blur background layer first (pure blur, no color)
            subcompose("blur") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { renderEffect = blurEffect }
                )
            }.first().measure(constraints).place(0, 0)

            // Place the nav UI on top (sharp)
            navPlaceable.place(0, 0)
        }
    }
}
            // Place the nav UI on top (sharp)
