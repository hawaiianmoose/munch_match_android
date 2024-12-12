package com.hawaiianmoose.munchmatch.view.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme

@Composable
fun ErrorDialog(openDialog: MutableState<Boolean>, message: String) {
    if (openDialog.value) {
        Dialog(
            onDismissRequest = {
                openDialog.value = false
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.Inherit,
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(Modifier.padding(16.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.alert_fill),
                            "Warning",
                            tint = MaterialTheme.colorScheme.error,
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            stringResource(R.string.problem),
                            fontWeight = FontWeight.Bold,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Text(
                        message,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                    )
                    Row(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            bottom = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        Button(
                            onClick = { openDialog.value = false },
                            modifier = Modifier.weight(8f).height(LocalDimen.current.buttonHeight),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                stringResource(R.string.ok),
                                color = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun ErrorDialogPreview() {
    MunchMatchTheme {
        ErrorDialog(mutableStateOf(true), "An email address already exists for this account.")
    }
}