package com.hawaiianmoose.munchmatch.view.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.ui.theme.LocalDimen
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.viewmodel.ListViewModel

@Composable
fun NewListDialog(
    openDialog: MutableState<Boolean>,
    listViewModel: ListViewModel
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val errorState = remember { mutableStateOf(false) }

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
                        text = stringResource(R.string.create_list),
                        Modifier
                            .align(CenterHorizontally)
                            .padding(top = 12.dp),
                        fontSize = MaterialTheme.typography.displayMedium.fontSize
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 12.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = textState.value,
                            singleLine = true,
                            placeholder = { Text(text = stringResource(R.string.list_hint)) },
                            onValueChange = {
                                errorState.value = false
                                textState.value = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyMedium,
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                            isError = errorState.value,
                            supportingText = {
                                if (errorState.value) {
                                    Text(
                                        text = stringResource(R.string.list_name_validation),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.Transparent,
                                errorBorderColor = MaterialTheme.colorScheme.error
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 16.dp,
                            bottom = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        Button(
                            onClick = {
                                errorState.value = false
                                openDialog.value = false
                                textState.value = TextFieldValue()
                            },
                            modifier = Modifier
                                .weight(8f)
                                .height(LocalDimen.current.buttonHeight),
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
                                if (textState.value.text.isEmpty()) {
                                    errorState.value = true
                                } else {
                                    val newEateryList = EateryList(
//                                        IdGenerator.generateUniqueId(),
//                                        textState.value.text.trim(),
//                                        items = listOf(),
//                                        frequentItems = listOf(),
//                                        isSortedAlphabetically = true,
//                                        isShowingItemDetails = true,
//                                        isCompleteSorted = false,
//                                        lastUpdated = Instant.now().epochSecond,
//                                        sharedUsers = emptyList()
                                    )
                                    listViewModel.addNewEateryList(newEateryList)
                                    openDialog.value = false
                                    textState.value = TextFieldValue()
                                }
                            },
                            modifier = Modifier
                                .weight(8f)
                                .height(LocalDimen.current.buttonHeight),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                stringResource(R.string.create),
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
fun NewListDialogPreview() {
    MunchMatchTheme {
        NewListDialog(mutableStateOf(true), ListViewModel())
    }
}