package com.hawaiianmoose.munchmatch.view

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.FontFamilies
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.util.Validator
import com.hawaiianmoose.munchmatch.view.control.EmailTextInput
import com.hawaiianmoose.munchmatch.view.control.GreenSwitch
import com.hawaiianmoose.munchmatch.view.control.LoadableGreenButton
import com.hawaiianmoose.munchmatch.view.control.PasswordInput
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import com.hawaiianmoose.munchmatch.view.control.UsernameTextInput
import com.hawaiianmoose.munchmatch.view.control.noRippleClickable
import com.hawaiianmoose.munchmatch.view.dialog.ErrorDialog

@Composable
fun SignUpView(navigator: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val checkedPolicyState = remember { mutableStateOf(true) }
    val emailTextState = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val usernameTextState = remember { mutableStateOf(TextFieldValue()) }
    val usernameErrorState = remember { mutableStateOf(false) }
    val pwErrorState = remember { mutableStateOf(false) }
    val pwMatchState = remember { mutableStateOf(true) }
    val loadingState = remember { mutableStateOf(false) }
    val errorState = remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val termsMessage = stringResource(R.string.terms_validation)

    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.privacy_and_terms))
        pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy") //TODO PP URL
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
            append(stringResource(R.string.policy))
        }
        pop()
        append(stringResource(id = R.string.spaced_and))
        pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms") //TODO TERMS URL
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
            append(stringResource(R.string.terms))
        }
        pop()
    }

    ErrorDialog(errorState, errorText)

    Column(
        Modifier
            .zIndex(1f)
            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp, top = 64.dp)
            .verticalScroll(scrollState, true)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                "Find your perfect match and make meal time amore!",
                fontFamily = FontFamilies.italic,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 26.sp
            )
            Spacer(Modifier.size(24.dp))
            UsernameTextInput(
                usernameTextState,
                stringResource(R.string.username_hint),
                focusManager,
                usernameErrorState
            )
            Spacer(Modifier.size(8.dp))
            EmailTextInput(
                emailTextState,
                stringResource(R.string.email_hint),
                focusManager,
                emailErrorState
            )
            Spacer(Modifier.size(8.dp))
            PasswordInput(
                focusManager = focusManager,
                errorState = pwErrorState,
                value = password
            ) { password = it }
            Spacer(Modifier.size(8.dp))
            PasswordInput(
                stringResource(R.string.confirm_password),
                focusManager = focusManager,
                errorState = pwErrorState,
                isMatched = pwMatchState,
                matchingValue = password,
                true,
                confirmPassword
            ) { confirmPassword = it }
            Row(
                modifier = Modifier
                    .padding(bottom = 12.dp, top = 0.dp)
                    .fillMaxWidth()
            )
            {
                ClickableText(
                    text = annotatedString,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .weight(5f)
                        .align(Alignment.CenterVertically),
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "policy", start = offset, end = offset
                        ).firstOrNull()?.let {
                            Log.d("policy URL", it.item)
                        }
                        annotatedString.getStringAnnotations(
                            tag = "terms", start = offset, end = offset
                        ).firstOrNull()?.let {
                            Log.d("terms URL", it.item)
                        }
                    })
                GreenSwitch(checkedPolicyState, Modifier.weight(1f))
            }
            Spacer(Modifier.size(32.dp))
            LoadableGreenButton(isLoading = loadingState, label = stringResource(R.string.signup_button)) {
                val isPasswordValid = Validator.validatePasswords(
                    password,
                    confirmPassword,
                    pwErrorState,
                    pwMatchState
                )
                val termsAccepted = checkedPolicyState.value

                if (!termsAccepted) {
                    Toast.makeText(
                        context,
                        termsMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }

                if (usernameTextState.value.text.isBlank()) {
                    usernameErrorState.value = true
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailTextState.value.text).matches()) {
                    emailErrorState.value = true
                }

                if (Patterns.EMAIL_ADDRESS.matcher(emailTextState.value.text).matches() && isPasswordValid && termsAccepted
                ) {
//                    loadingState.value = true
//                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailTextState.value.text, password)
//                        .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            task.result.user?.email?.let { email ->
//                                val newProfile = UserProfile(userName = email, isNewUser = true)
//                                coroutineScope.launch {
//                                    DataStoreProvider.storeUserProfile(newProfile)
//                                }
//                                loadingState.value = false
//                                Navigation.rootNavigator?.popBackStack()
//                                Navigation.rootNavigator?.navigate(ListHomeViewDestination())
//                            }
//                        }
//                    }
//                        .addOnFailureListener { fail ->
//                            errorText = fail.message.toString()
//                            errorState.value = true
//                            loadingState.value = false
//                        }
                }
            }
            Box(modifier = Modifier.zIndex(0f), contentAlignment = BottomCenter
            ) {
                SparkleFooter()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                        .padding(top = 32.dp, bottom = 16.dp)
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                ) {
                    Text(
                        stringResource(R.string.have_an_account),
                        fontFamily = FontFamilies.italic,
                        fontSize = 14.sp
                    )
                    TextButton(
                        onClick = {},
                       ) {
                        Text(
                            stringResource(R.string.signin_button),
                            modifier = Modifier.noRippleClickable { navigator.navigate("signin") })
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SignUpPreview() {
    MunchMatchTheme {
        SignUpView(NavHostController(LocalContext.current))
    }
}
