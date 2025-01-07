package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun SparkleFooter() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .offset(y = (-20).dp)) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.sparkle_footer),
            contentDescription = "Footer Image",
            modifier = Modifier
                .rotate(160F)
                .align(Alignment.CenterStart)
                .offset(y = (-34).dp, x = (16).dp)
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.sparkle_footer),
            contentDescription = "Footer Image",
            modifier = Modifier
                .rotate(120F)
                .align(Alignment.CenterEnd)
                .graphicsLayer {
                    rotationX = 180f
                }
        )
    }
}

@Composable
@Preview
fun FooterPreview() {
    MunchMatchTheme {
        SparkleFooter()
    }
}