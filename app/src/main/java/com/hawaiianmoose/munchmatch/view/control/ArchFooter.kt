package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun ArchFooter() {
    Column(Modifier.fillMaxWidth()){
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.onBackground
                        )
                    )
                )
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.arch_footer),
            contentDescription = "Arch Footer Image",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun ArchFooterPreview() {
    MunchMatchTheme {
        ArchFooter()
    }
}