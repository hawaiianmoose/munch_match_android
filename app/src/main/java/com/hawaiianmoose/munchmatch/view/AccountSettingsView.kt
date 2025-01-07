package com.hawaiianmoose.munchmatch.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.data.DataStoreInitializer
import com.hawaiianmoose.munchmatch.data.DataStoreProvider
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import com.hawaiianmoose.munchmatch.view.control.noRippleClickable
import com.hawaiianmoose.munchmatch.view.dialog.DeleteAccountDialog
import com.hawaiianmoose.munchmatch.view.dialog.SignOutDialog
import com.hawaiianmoose.munchmatch.view.dialog.UpdatePasswordModal

@Composable
fun AccountSettingsView(navigator: NavHostController) {
    var showUpdatePasswordModal by remember { mutableStateOf(false) }
    val openDeleteAccountDialog = remember { mutableStateOf(false) }
    val openSignOutDialog = remember { mutableStateOf(false)  }
    SignOutDialog(openSignOutDialog, navigator)
    DeleteAccountDialog(openDialog = openDeleteAccountDialog, navigator)

    Scaffold(
        topBar = { Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(140.dp)
                .padding(bottom = 8.dp)
                .background(MaterialTheme.colorScheme.primary),
            content = {
                Column(modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 24.dp)
                    .align(Alignment.CenterStart)) {
                    Row(Modifier.offset(x = -8.dp, y = 4.dp)) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.round_back_arrow),
                                "Back",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp).offset(x = -10.dp).noRippleClickable {
                                    navigator.navigate("listhome")
                                }
                            )
                        }
                        Text(
                            text = stringResource(R.string.my_munch_lists),
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(CenterVertically)
                                .offset(x = -18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = stringResource(R.string.account_settings),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        )},
        bottomBar = {
            Box(Modifier.fillMaxWidth()) {
                SparkleFooter()
                Column(modifier = Modifier
                    .align(BottomCenter)
                    .padding(24.dp)
                    ,horizontalAlignment = CenterHorizontally) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(LocalDimen.current.buttonHeight),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = MaterialTheme.colorScheme.onBackground,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary),
                        onClick = { openSignOutDialog.value = true }) {
                        Text(modifier = Modifier.padding(4.dp), text = stringResource(R.string.sign_out))
                    }
                    Spacer(Modifier.size(20.dp))
                    androidx.compose.material3.TextButton(
                        onClick = {}) {
                        Text(text = stringResource(R.string.delete_account), color = MaterialTheme.colorScheme.error, fontSize = 16.sp, modifier = Modifier
                            .padding(bottom = 16.dp)
                            .noRippleClickable {
                                openDeleteAccountDialog.value = true
                            })
                    }
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        val notUsed = paddingValues
        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = DataStoreProvider.getStoredUserProfile().userName,
                onValueChange = {},
                enabled = false,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalDimen.current.buttonHeight)
                    //.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp)
                    .focusable(false),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    placeholderColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(6.dp),
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Lock, "Locked", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            )
            Spacer(Modifier.size(24.dp))
            UpdatePasswordModal(
                onDismissRequest = { showUpdatePasswordModal = false },
                showModalTransitionDialog = showUpdatePasswordModal,
                onShowDialog = { showUpdatePasswordModal = true }
            )
        }
    }
}

@Preview
@Composable
fun AccountSettingsViewPreview() {
    MunchMatchTheme {
        DataStoreInitializer.initDataStore(LocalContext.current)
        AccountSettingsView(NavHostController(LocalContext.current))
    }
}