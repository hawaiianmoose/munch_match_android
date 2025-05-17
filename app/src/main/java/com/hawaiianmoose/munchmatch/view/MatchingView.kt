package com.hawaiianmoose.munchmatch.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.model.MatchSession
import kotlin.math.absoluteValue

@Composable
fun MatchingView(matchSession: MatchSession, navigator: NavHostController) {
    var currentIndex by remember { mutableStateOf(0) }
    val rotation = remember { Animatable(0f) }
    var pendingSwipeDirection by remember { mutableStateOf<Int?>(null) }
    var textColor by remember { mutableStateOf(Color.White) }

    val currentText =
        if (currentIndex < matchSession.selectedList.eateries.size)
            matchSession.selectedList.eateries[currentIndex].name else "Your Picks Are In!"

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Launch animation when a swipe is detected
        LaunchedEffect(pendingSwipeDirection) {
            val direction = pendingSwipeDirection
            if (direction != null && currentIndex < matchSession.selectedList.eateries.size) {
                // Set highlight color
                textColor = if (direction > 0) Color.Green else Color.Red

                rotation.snapTo(0f)
                rotation.animateTo(
                    targetValue = 90f * direction,
                    animationSpec = tween(durationMillis = 300)
                )
                currentIndex++
                textColor = Color.White // Reset color for new card
                rotation.snapTo(-90f * direction)
                rotation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
                pendingSwipeDirection = null
            } else if(currentIndex == matchSession.selectedList.eateries.size) {
                navigator.popBackStack()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .background(Color.Black)
                .pointerInput(currentIndex) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            if (pendingSwipeDirection == null && dragAmount.absoluteValue > 50f && currentIndex < matchSession.selectedList.eateries.size) {
                                pendingSwipeDirection = if (dragAmount > 0) 1 else -1
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentText,
                fontSize = 32.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(32.dp)
                    .graphicsLayer {
                        rotationY = rotation.value
                        cameraDistance = 8 * density
                    }
            )
            //TODO details and make this a column not a box
        }
    }
}