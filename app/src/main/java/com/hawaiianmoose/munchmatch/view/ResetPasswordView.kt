package com.hawaiianmoose.munchmatch.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.FontFamilies
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.util.Validator
import com.hawaiianmoose.munchmatch.view.control.GreenButton
import com.hawaiianmoose.munchmatch.view.control.PasswordInput
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter

@Composable
fun ResetPasswordView(navigator: NavHostController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    val pwErrorState = remember { mutableStateOf(false) }
    val pwMatchState = remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)) {
        Text(
            stringResource(R.string.new_password),
            fontFamily = FontFamilies.italic,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 28.dp, end = 28.dp, bottom = 24.dp)
        )
        PasswordInput(focusManager = focusManager, errorState = pwErrorState, value = newPassword) { newPassword = it }
        Spacer(Modifier.size(24.dp))
        PasswordInput(stringResource(R.string.confirm_password), focusManager = focusManager, errorState = pwErrorState, isMatched = pwMatchState, matchingValue = newPassword, isClearFocus = true, confirmNewPassword) { confirmNewPassword = it }
        Column(modifier = Modifier.padding(top = 36.dp)) {
            GreenButton(label = stringResource(id = R.string.save_and_sign_in)) {
                val isPasswordValid = Validator.validatePasswords(
                    newPassword,
                    confirmNewPassword,
                    pwErrorState,
                    pwMatchState
                )

                if (isPasswordValid) {
                    navigator.popBackStack()
                    //TODO SIGN IN OR SEND TO SIGN IN PAGE?
                    navigator.navigate("listhome")
                }
            }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SparkleFooter()
    }
}

@Composable
@Preview
fun ResetPasswordPreview() {
    MunchMatchTheme {
        ResetPasswordView(NavHostController(LocalContext.current))
    }
}