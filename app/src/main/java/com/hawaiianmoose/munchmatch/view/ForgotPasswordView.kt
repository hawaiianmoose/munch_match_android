package com.hawaiianmoose.munchmatch.view

import android.util.Patterns
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.FontFamilies
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.control.BlueButton
import com.hawaiianmoose.munchmatch.view.control.EmailTextInput

@Composable
fun ForgotPasswordView(email: String, navigator: NavHostController) {
    val focusManager = LocalFocusManager.current
    val emailTextState = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val isSuccessTextVisible = remember { mutableStateOf(false) }

    if (email.isNotEmpty()) {
        emailTextState.value = TextFieldValue(email)
    }

    Scaffold(
        topBar = {},
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
                            navigator.popBackStack()
                            navigator.navigate("signin")
                            isSuccessTextVisible.value = false
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
                            stringResource(R.string.go_back_to_login),
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
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                .zIndex(1f)
        ) {
            Text(
                stringResource(R.string.reset_password),
                fontFamily = FontFamilies.italic,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.size(32.dp))

            EmailTextInput(
                emailTextState,
                stringResource(R.string.email_hint),
                focusManager,
                emailErrorState
            )
            Spacer(Modifier.size(8.dp))

            BlueButton(label = stringResource(id = R.string.reset_password_button)) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailTextState.value.text).matches()) {
                    //TODO call reset password
                    isSuccessTextVisible.value = true
                } else {
                    emailErrorState.value = true
                }
            }

            Spacer(Modifier.size(16.dp))

            if (isSuccessTextVisible.value) {
                Text(
                    stringResource(R.string.reset_password_sent),
                    fontFamily = FontFamilies.italic,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview
fun ForgotPasswordPreview() {
    MunchMatchTheme {
        ForgotPasswordView("tester@adadapted.com", NavHostController(LocalContext.current))
    }
}