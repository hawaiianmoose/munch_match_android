package com.hawaiianmoose.munchmatch.view.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hawaiianmoose.munchmatch.util.ANIMATION_TIME
import com.hawaiianmoose.munchmatch.util.AnimatedModalTransition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Composable
fun ModalTransitionDialog(
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = true,
    content: @Composable (ModalTransitionDialogHelper) -> Unit
) {
    val onCloseSharedFlow: MutableSharedFlow<Unit> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateContentBackTrigger = remember { mutableStateOf(false) }
    val mutex = remember { Mutex(true) }

    LaunchedEffect(key1 = Unit) {
        launch {
            mutex.withLock {
                animateContentBackTrigger.value = true
            }
        }
        launch {
            onCloseSharedFlow.asSharedFlow().collectLatest { startDismissWithExitAnimation(animateContentBackTrigger, onDismissRequest) }
        }
    }

    Dialog(
        onDismissRequest = { coroutineScope.launch { startDismissWithExitAnimation(animateContentBackTrigger, onDismissRequest) } },
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnBackPress = dismissOnBackPress, dismissOnClickOutside = false)
    ) {
        LaunchedEffect(key1 = Unit) {
            if (mutex.isLocked) mutex.unlock()
        }

        AnimatedModalTransition(visible = animateContentBackTrigger.value) {
            content(ModalTransitionDialogHelper(coroutineScope, onCloseSharedFlow))
        }
    }
}

private suspend fun startDismissWithExitAnimation(
    animateContentBackTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateContentBackTrigger.value = false
    delay(ANIMATION_TIME)
    onDismissRequest()
}

class ModalTransitionDialogHelper(
    private val coroutineScope: CoroutineScope,
    private val onCloseFlow: MutableSharedFlow<Unit>
) {
    fun triggerAnimatedClose() {
        coroutineScope.launch {
            onCloseFlow.emit(Unit)
        }
    }
}