package com.hawaiianmoose.munchmatch.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.FontFamilies
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.util.rememberScreenInfo
import com.hawaiianmoose.munchmatch.view.control.ArchHeader
import com.hawaiianmoose.munchmatch.view.control.EmailTextInput
import com.hawaiianmoose.munchmatch.view.control.GreenButton
import com.hawaiianmoose.munchmatch.view.control.PasswordInput
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import com.hawaiianmoose.munchmatch.view.control.noRippleClickable
import com.hawaiianmoose.munchmatch.view.dialog.ErrorDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignInView(navigator: NavHostController) {
    val scrollState = rememberScrollState()
    val isScreenOverflow = rememberScreenInfo().screenHeight < 720.dp
    val focusManager = LocalFocusManager.current
    var password by remember { mutableStateOf("") }
    val emailTextState = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val pwErrorState = remember { mutableStateOf(false) }
    val loadingState = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val errorState = remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    ErrorDialog(errorState, errorText)

    Scaffold(
        topBar = { ArchHeader() },
    ) { paddingValues ->
        Log.d(paddingValues.toString(), "")
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .zIndex(1f)
                    .padding(start = 24.dp, end = 24.dp, bottom = 12.dp, top = 12.dp)
                    .verticalScroll(scrollState, isScreenOverflow)
                    .fillMaxWidth()
            ) {
                EmailTextInput(
                    emailTextState,
                    stringResource(R.string.username_email_hint),
                    focusManager,
                    emailErrorState
                )
                Spacer(Modifier.size(8.dp))
                Box {
                    PasswordInput(
                        focusManager = focusManager,
                        errorState = pwErrorState,
                        value = password
                    )
                    { password = it }
                    TextButton(
                        onClick = {},
                        modifier = Modifier
                            .align(BottomEnd)
                            .offset(y = 18.dp, x = 8.dp)
                    ) {
                        Text(
                            stringResource(R.string.forgot_password_button),
                            fontSize = 12.sp,
                            modifier = Modifier.noRippleClickable {
                                //navigator.navigate(ResetPasswordViewDestination) //FOR TESTING THIS VIEW ONLY
                                //navController.navigate(ForgotPasswordViewDestination(emailTextState.value.text))
                            })
                    }
                }
                Spacer(Modifier.size(20.dp))
                GreenButton(label = stringResource(R.string.signin_button)) {
                    completeSignIn(
                        "userId",
                        coroutineScope,
                        loadingState,
                        emailTextState,
                        navigator
                    )//temp
//                if (Firebase.auth.currentUser == null) {
//                    if (Patterns.EMAIL_ADDRESS.matcher(emailTextState.value.text).matches()) {
//                        loadingState.value = true
//                        Firebase.auth.signInWithEmailAndPassword(emailTextState.value.text, password)
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) { task.result.user?.email?.let {
//                                    completeSignIn(it, coroutineScope, loadingState, emailTextState)
//                                }
//                                }
//                            }
//                            .addOnFailureListener { fail ->
//                                loadingState.value = false
//                                errorText = fail.message.toString()
//                                errorState.value = true
//                            }
//                    } else {
//                        emailErrorState.value = true
//                    }
//                }
                }

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.or_divider),
                    contentDescription = "Divider",
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 40.dp, bottom = 0.dp, start = 32.dp, end = 32.dp)
                )

                Box(
                    modifier = Modifier.zIndex(0f), contentAlignment = Alignment.BottomCenter
                ) {
                    SparkleFooter()
                    Column(
                        horizontalAlignment = CenterHorizontally, modifier = Modifier
                            .padding(top = 32.dp, bottom = 16.dp)
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.dont_have_an_account),
                            fontFamily = FontFamilies.italic,
                            fontSize = 14.sp
                        )
                        TextButton(
                            onClick = {},
                        ) {
                            Text(
                                stringResource(R.string.signup_button),
                                modifier = Modifier.noRippleClickable {
                                    navigator.navigate("signup")
                                })
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .rotate(30f)
                        .graphicsLayer {
                            scaleY = -1f
                        }
                ) {
                    SparkleFooter()
                }
            }
            if (loadingState.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = (-64).dp)
                        .size(120.dp)
                        .zIndex(2f),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 6.dp
                )
            }
        }
    }
}

private fun completeSignIn(
    userId: String,
    coroutineScope: CoroutineScope,
    loadingState: MutableState<Boolean>,
    emailTextState: MutableState<TextFieldValue>,
    navigator: NavHostController
) {
//    UserProfileClient.fetchOrCreateUserProfile(userId, onSuccess = { userProfile ->
//        coroutineScope.launch {
//            launch {
//                DataStoreProvider.storeUserProfile(userProfile)
//            }
//            DataStoreProvider.syncListsFromNetwork(userProfile.listIds)
//            loadingState.value = false
//            //Navigation.rootNavigator?.popBackStack() //close off account back stack
//            //Navigation.rootNavigator?.navigate(ListHomeViewDestination())
//            navController.navigate(ListHomeViewDestination())
//            emailTextState.value = TextFieldValue()
//        }
//    })
    navigator.popBackStack() //close off account back stack
    navigator.navigate("listhome")
}

@Composable
@Preview
fun Preview() {
    MunchMatchTheme {
        SignInView(NavHostController(LocalContext.current))
    }
}