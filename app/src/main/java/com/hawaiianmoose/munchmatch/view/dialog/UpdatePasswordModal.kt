package com.hawaiianmoose.munchmatch.view.dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.util.Validator
import com.hawaiianmoose.munchmatch.view.control.GreenButton
import com.hawaiianmoose.munchmatch.view.control.ModalDialogHeader
import com.hawaiianmoose.munchmatch.view.control.PasswordInput
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun UpdatePasswordModal(
    onShowDialog: () -> Unit,
    onDismissRequest: () -> Unit,
    showModalTransitionDialog: Boolean
) {
    Button(
        onClick = onShowDialog,
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalDimen.current.buttonHeight)
            .focusable(),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            stringResource(R.string.update_password),
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }

    if (showModalTransitionDialog) {
        UpdatePasswordModalTransitionDialog(onDismissRequest)
    }
}

@Composable
fun UpdatePasswordModalTransitionDialog(onDismissRequest: () -> Unit)
{
    var oldPassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val oldPwErrorState = remember { mutableStateOf(false) }
    val pwErrorState = remember { mutableStateOf(false) }
    val pwMatchState = remember { mutableStateOf(true) }
    val localContext = LocalContext.current
    val savedPasswordMessage = stringResource(R.string.new_password_saved)

    ModalTransitionDialog(onDismissRequest = onDismissRequest) { modalTransitionDialogHelper ->
        val focusManager = LocalFocusManager.current
        Scaffold(
            topBar = {
                ModalDialogHeader(stringResource(R.string.update_password), modalTransitionDialogHelper)
            },
            bottomBar = {
                Box(Modifier.fillMaxWidth()) {
                    SparkleFooter()
                    Column(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 28.dp, start = 24.dp, end = 24.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                val isPasswordValid = Validator.validatePasswords(
                                    password,
                                    confirmPassword,
                                    pwErrorState,
                                    pwMatchState
                                )
                                if (isPasswordValid) {
                                    updatePassword(
                                        oldPassword,
                                        password,
                                        localContext,
                                        savedPasswordMessage,
                                        modalTransitionDialogHelper
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(LocalDimen.current.buttonHeight)
                                .focusable(),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                disabledContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                stringResource(R.string.save),
                                color = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            },
            backgroundColor = MaterialTheme.colorScheme.onBackground
        ) { paddingValues ->
            var unused = paddingValues
            Column(modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                .zIndex(1f)) {
                PasswordInput(focusManager = focusManager, value = oldPassword, errorState = oldPwErrorState, placeholderText = stringResource(R.string.old_password_update)) { oldPassword = it }
                Spacer(Modifier.size(48.dp))
                PasswordInput(focusManager = focusManager, value = password, errorState = pwErrorState, placeholderText = stringResource(R.string.new_password_update)) { password = it }
                Spacer(Modifier.size(24.dp))
                PasswordInput(
                    stringResource(R.string.new_password_confirm), focusManager = focusManager, errorState = pwErrorState, isMatched = pwMatchState, matchingValue = password, true,
                    confirmPassword
                ) { confirmPassword = it }
            }
        }
    }
}

fun updatePassword(
    oldPassword: String,
    newPassword: String,
    localContext: Context,
    savedPasswordMessage: String,
    modalTransitionDialogHelper: ModalTransitionDialogHelper
) {
    val user = FirebaseAuth.getInstance().currentUser
    val email = user?.email
    if (email != null) {
        val credential: AuthCredential = EmailAuthProvider.getCredential(email, oldPassword)

        user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
            if (reAuthTask.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        Toast.makeText(
                            localContext,
                            savedPasswordMessage,
                            Toast.LENGTH_LONG
                        ).show()
                        modalTransitionDialogHelper::triggerAnimatedClose.invoke()
                    } else {
                        Toast.makeText(
                            localContext,
                            "Error: ${updateTask.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    localContext,
                    "Error: Old password incorrect",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Composable
@Preview
fun UpdatePwPreview() {
    var oldPassword by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val pwErrorState = remember { mutableStateOf(false) }
    val pwMatchState = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val onCloseSharedFlow: MutableSharedFlow<Unit> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
   MunchMatchTheme {
       Scaffold(
           topBar = {
               ModalDialogHeader(stringResource(R.string.update_password), ModalTransitionDialogHelper(coroutineScope, onCloseSharedFlow))
           },
           bottomBar = {
               Box(Modifier.fillMaxWidth()) {
                   SparkleFooter()
                   Column(
                       Modifier
                           .align(Alignment.BottomCenter)
                           .padding(bottom = 28.dp, start = 24.dp, end = 24.dp),
                       horizontalAlignment = CenterHorizontally
                   ) {
                       GreenButton(label = stringResource(R.string.save), clickFunc = {
                           /*todo save pw*/
                           //modalTransitionDialogHelper::triggerAnimatedClose.invoke()
                       })
                   }
               }
           },
           backgroundColor = MaterialTheme.colorScheme.onBackground
       ) { paddingValues ->
           var unused = paddingValues
           Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)) {
               PasswordInput(focusManager = focusManager, value = oldPassword, errorState = pwErrorState, placeholderText = stringResource(R.string.old_password_update)) { password = it }
               Spacer(Modifier.size(48.dp))
               PasswordInput(focusManager = focusManager, value = password, errorState = pwErrorState, placeholderText = stringResource(R.string.new_password_update)) { password = it }
               Spacer(Modifier.size(24.dp))
               PasswordInput(
                   stringResource(R.string.new_password_confirm),
                   focusManager = focusManager, errorState = pwErrorState, isMatched = pwMatchState, isClearFocus = true,
                   value = confirmPassword
               ) { confirmPassword = it }
           }
       }
   }
}