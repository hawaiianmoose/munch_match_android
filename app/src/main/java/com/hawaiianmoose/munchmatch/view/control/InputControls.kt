package com.hawaiianmoose.munchmatch.view.control

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.R

@Composable
fun UsernameTextInput(
    textState: MutableState<TextFieldValue>,
    hint: String,
    focusManager: FocusManager,
    errorState: MutableState<Boolean>
) {
    androidx.compose.material3.OutlinedTextField(
        value = textState.value,
        singleLine = true,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.labelMedium
            )
        },
        onValueChange = {
            textState.value = it
            errorState.value = false
        },
        isError = errorState.value,
        supportingText = {
            if (errorState.value) {
                Text(
                    text = stringResource(R.string.email_validation),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = RoundedCornerShape(6.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        })
    )
}

@Composable
fun EmailTextInput(
    textState: MutableState<TextFieldValue>,
    hint: String,
    focusManager: FocusManager,
    errorState: MutableState<Boolean>
) {
    androidx.compose.material3.OutlinedTextField(
        value = textState.value,
        singleLine = true,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.labelMedium
            )
        },
        onValueChange = {
            textState.value = it
            errorState.value = false
        },
        isError = errorState.value,
        supportingText = {
            if (errorState.value) {
                Text(
                    text = stringResource(R.string.email_validation),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = RoundedCornerShape(6.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        })
    )
}

@Composable
fun PasswordInput(
    placeholderText: String = stringResource(R.string.password_hint),
    focusManager: FocusManager,
    errorState: MutableState<Boolean>,
    isMatched: MutableState<Boolean>? = null,
    matchingValue: String = "",
    isClearFocus: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var lengthMet by remember { mutableStateOf(false) }

    androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            lengthMet = it.length >= 8
            if(it == matchingValue) {
                isMatched?.value = true
            }
        },
        singleLine = true,
        isError = errorState.value,
        supportingText = {
            if (errorState.value) {
                if (!lengthMet) {
                    Text(
                        text = stringResource(R.string.pw_length),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else if (isMatched?.value == false) {
                    Text(
                        text = stringResource(R.string.pw_match),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = { Text(placeholderText, style = MaterialTheme.typography.labelMedium) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Color.Transparent,
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        shape = RoundedCornerShape(6.dp),
        keyboardActions = KeyboardActions(onNext =  {
            if(isClearFocus) {
                focusManager.clearFocus()
            } else {
                focusManager.moveFocus(FocusDirection.Down)
            }
        }),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            // Toggle button to hide or display password
            IconButton(onClick = {}) {
                Icon(imageVector = image, description, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.noRippleClickable { passwordVisible = !passwordVisible })
            }
        }
    )
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun GreenButton(label: String, clickFunc: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = { clickFunc() },
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
            label,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun BlueButton(label: String, clickFunc: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = { clickFunc() },
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalDimen.current.buttonHeight)
            .focusable(),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            label,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun LobbyButton(label: String, clickFunc: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = { clickFunc() },
        modifier = Modifier
            .height(LocalDimen.current.buttonHeight)
            .focusable(),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            label,
            color = Color.White
        )
    }
}

@Composable
fun LoadableGreenButton(isLoading: MutableState<Boolean>, label: String, clickFunc: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = { clickFunc() },
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalDimen.current.buttonHeight)
            .focusable(),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = label,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            if (isLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun InputTextPreview() {
    MunchMatchTheme {
        //var password by remember { mutableStateOf("") }
        val errorState = remember { mutableStateOf(false) }
        val emailTextState = remember { mutableStateOf(TextFieldValue()) }
        Column {
            GreenButton("Sign In", {})
            EmailTextInput(emailTextState, "Username/Email", LocalFocusManager.current, errorState)
            //PasswordInput(value = password) { password = it }
        }
    }
}