package com.hawaiianmoose.munchmatch.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat

private val ColorScheme = lightColorScheme(
    primary = PrimaryRed,
    secondary = SecondaryGold,
    tertiary = PrimaryRed,
    primaryContainer = PrimaryContainer,
    tertiaryContainer = ButtonGreen,
    onBackground = BackgroundLight,
    secondaryContainer = SecondaryBlue25,
    onSecondaryContainer = ListBlue,
    error = ErrorPink,
    inverseSurface = ProgressTrackGreen,
    onPrimaryContainer = PrimaryBlueModal,
    onSurface = FadedContainerBlue,
    onSurfaceVariant = FadedFillBlue,
    onTertiary = Gray,
    surfaceVariant = TransparentWhite
)

@Composable
fun MunchMatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> ColorScheme
        else -> ColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(2.dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = shapes
    )
}