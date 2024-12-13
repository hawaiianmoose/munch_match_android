package com.hawaiianmoose.munchmatch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import com.hawaiianmoose.munchmatch.R

object FontFamilies {
    val default = FontFamily(Font(R.font.avenir_light))
    val italic = FontFamily(Font(R.font.lora_italic))
}

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = SecondaryBlue50
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    displayLarge = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlueModal
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamilies.default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = PrimaryBlue
    )
)
