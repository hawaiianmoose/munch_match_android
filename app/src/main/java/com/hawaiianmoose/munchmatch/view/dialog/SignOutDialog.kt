package com.hawaiianmoose.munchmatch.view.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.data.DataStoreProvider
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import kotlinx.coroutines.launch

@Composable
fun SignOutDialog(openDialog: MutableState<Boolean>, navigator: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
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
                Column(modifier = Modifier.background(Color.White)) {
                    Text(
                        text = stringResource(R.string.sign_out),
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 12.dp),
                        fontSize = MaterialTheme.typography.displayMedium.fontSize
                    )
                    Text(
                        text = stringResource(R.string.sign_out_sure),
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(12.dp),
                        fontSize = MaterialTheme.typography.displaySmall.fontSize
                    )
                    Row(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 16.dp,
                            bottom = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        Button(
                            onClick = { openDialog.value = false },
                            modifier = Modifier.weight(8f).height(LocalDimen.current.buttonHeight),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                stringResource(R.string.cancel),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.5f))
                        Button(
                            onClick = {
                                openDialog.value = false
                                coroutineScope.launch {
                                    DataStoreProvider.deleteDataStore()
                                }
                                navigator.clearBackStack<Any>()
                                navigator.navigate("signin")
                            },
                            modifier = Modifier.weight(8f).height(LocalDimen.current.buttonHeight),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                stringResource(R.string.sign_out),
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
fun SignOutDialogPreview() {
    MunchMatchTheme {
        SignOutDialog(mutableStateOf(true), NavHostController(LocalContext.current))
    }
}