package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> MaterialTheme.colorScheme.error
            DismissValue.DismissedToEnd -> Color.Transparent
            DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
        }
    )
    val alignment = Alignment.CenterEnd
    val icon = Icons.Default.Delete
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1.5f
    )

    Box(Modifier.fillMaxSize()
            .padding(top = 2.dp, bottom = 2.dp, start = 32.dp, end = 6.dp)
            .clip(RoundedCornerShape(
                    topStart = 4.dp,
                    bottomStart = 4.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp,
                )
            )
    )
    {
        Box(Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
            contentAlignment = alignment
        ) {
            Icon(
                icon,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier.scale(scale)
            )
        }
    }
}
