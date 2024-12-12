package com.hawaiianmoose.munchmatch.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimen = compositionLocalOf { Dimensions() }

data class Dimensions(val buttonHeight: Dp = 54.dp)
