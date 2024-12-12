package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.dialog.ModalTransitionDialogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun ModalDialogHeader(title: String, modalTransitionDialogHelper: ModalTransitionDialogHelper) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(84.dp)
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(0.dp, 0.dp, 28.dp, 28.dp))
                .background(MaterialTheme.colorScheme.onPrimaryContainer),
            content = {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    onClick = modalTransitionDialogHelper::triggerAnimatedClose
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.down_arrow),
                        stringResource(R.string.desc_back),
                        tint = Color.White
                    )
                }
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .offset(x = 0.dp, y = -2.dp)
                        .align(Alignment.Center)
                )
            }
        )
    }
}

@Composable
@Preview
fun PreviewModalHeader() {
    MunchMatchTheme {
        val onCloseSharedFlow: MutableSharedFlow<Unit> = remember { MutableSharedFlow() }
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        ModalDialogHeader(
            title = "Preview Title", modalTransitionDialogHelper = ModalTransitionDialogHelper(
                coroutineScope, onCloseSharedFlow
            )
        )
    }
}