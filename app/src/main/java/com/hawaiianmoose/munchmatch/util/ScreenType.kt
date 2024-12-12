package com.hawaiianmoose.munchmatch.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberScreenInfo(): ScreenInfo {
    val config = LocalConfiguration.current
    return ScreenInfo(
        screenWidthInfo = when {
            config.screenWidthDp < 600 -> ScreenInfo.ScreenType.Compact
            config.screenHeightDp < 840 -> ScreenInfo.ScreenType.Medium
            else -> ScreenInfo.ScreenType.Expanded
        },
        screenHeightInfo = when {
            config.screenHeightDp < 480 -> ScreenInfo.ScreenType.Compact
            config.screenHeightDp < 900 -> ScreenInfo.ScreenType.Medium
            else -> ScreenInfo.ScreenType.Expanded
        },
        screenWidth = config.screenWidthDp.dp,
        screenHeight = config.screenHeightDp.dp
    )
}

data class ScreenInfo(
    val screenWidthInfo: ScreenType,
    val screenHeightInfo: ScreenType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class ScreenType {
        object Compact : ScreenType()
        object Medium : ScreenType()
        object Expanded : ScreenType()
    }
}
