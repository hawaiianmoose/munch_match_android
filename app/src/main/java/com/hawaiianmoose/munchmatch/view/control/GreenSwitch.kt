package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun GreenSwitch(checkState: MutableState<Boolean>, modifier: Modifier) {
    Switch(
        checked = checkState.value,
        onCheckedChange = { checkState.value = it },
        colors = SwitchDefaults.colors(
            checkedTrackColor = MaterialTheme.colorScheme.tertiaryContainer,
            uncheckedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
            uncheckedTrackColor = MaterialTheme.colorScheme.onTertiary,
            uncheckedThumbColor = Color.White
        ),
        modifier = modifier
    )
}

@Composable
@Preview
fun GreenSwitchPreview() {
    val checkedAlphaState = remember { mutableStateOf(false) }
    val mod = Modifier

    MunchMatchTheme {
        GreenSwitch(checkState = checkedAlphaState, modifier = mod)
    }
}