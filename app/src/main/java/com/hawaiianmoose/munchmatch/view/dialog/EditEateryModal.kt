package com.hawaiianmoose.munchmatch.view.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.FontFamilies
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.control.GreenButton
import com.hawaiianmoose.munchmatch.view.control.ModalDialogHeader
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import com.hawaiianmoose.munchmatch.viewmodel.ListDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun EditItemModal(
    listDetailViewModel: ListDetailViewModel,
    onDismissRequest: () -> Unit,
    showModalTransitionDialog: Boolean
) {
    if (showModalTransitionDialog) {
        EditItemModalTransitionDialog(onDismissRequest, listDetailViewModel)
    }
}

@Composable
fun EditItemModalTransitionDialog(onDismissRequest: () -> Unit, listDetailViewModel: ListDetailViewModel)
{
    val textState = remember { mutableStateOf(TextFieldValue(text = listDetailViewModel.currentEatery.value.name)) }
    val errorState = remember { mutableStateOf(false) }
    val detailState = remember { mutableStateOf(TextFieldValue(text = listDetailViewModel.currentEatery.value.details)) }
    val noteErrorState = remember { mutableStateOf(false) }

    fun saveItem() {
        listDetailViewModel.updateItem(
            textState.value.text.trim(),
            detailState.value.text.trim()
        )
    }

    ModalTransitionDialog(onDismissRequest = onDismissRequest) { modalTransitionDialogHelper ->
        Scaffold(
            topBar = {
                ModalDialogHeader(stringResource(R.string.edit_eatery), modalTransitionDialogHelper)
            },
            bottomBar = {
                Box(Modifier.fillMaxWidth()) {
                    SparkleFooter()
                    Column(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 28.dp, start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GreenButton(label = stringResource(R.string.save), clickFunc = {
                            saveItem()
                            modalTransitionDialogHelper::triggerAnimatedClose.invoke()
                        })
                    }
                }
            },
            backgroundColor = MaterialTheme.colorScheme.onBackground
        ) { paddingValues ->
            var unused = paddingValues
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)) {
                OutlinedTextField(
                    value = textState.value,
                    singleLine = true,
                    onValueChange = {
                        errorState.value = false
                        textState.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
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
                Spacer(Modifier.size(4.dp))
                Divider(thickness = 1.5.dp, color = MaterialTheme.colorScheme.secondaryContainer)
                Spacer(Modifier.size(16.dp))
                Text(stringResource(R.string.details),  fontFamily = FontFamilies.italic,)
                OutlinedTextField(
                    value = detailState.value,
                    onValueChange = {
                        noteErrorState.value = false
                        detailState.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    isError = noteErrorState.value,
                    supportingText = {
                        if (noteErrorState.value) {
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
                    shape = RoundedCornerShape(6.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        saveItem()
                        modalTransitionDialogHelper::triggerAnimatedClose.invoke()
                    })
                )
            }
        }
    }
}

@Composable
@Preview
fun EditItemModalPreview() {
    val textState = remember { mutableStateOf(TextFieldValue(text = "Strawberries")) }
    val errorState = remember { mutableStateOf(false) }
    val noteState = remember { mutableStateOf(TextFieldValue(text = "")) }
    val noteErrorState = remember { mutableStateOf(false) }
    val countState = remember { mutableStateOf(0) }
    val onCloseSharedFlow: MutableSharedFlow<Unit> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    MunchMatchTheme {
        Scaffold(
            topBar = {
                ModalDialogHeader(stringResource(R.string.edit_eatery), ModalTransitionDialogHelper(coroutineScope, onCloseSharedFlow))
            },
            bottomBar = {
                Box(Modifier.fillMaxWidth()) {
                    SparkleFooter()
                    Column(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 28.dp, start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GreenButton(label = stringResource(R.string.save), clickFunc = {})
                    }
                }
            },
            backgroundColor = MaterialTheme.colorScheme.onBackground
        ) { paddingValues ->
            var unused = paddingValues
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)) {
                OutlinedTextField(
                    value = textState.value,
                    singleLine = true,
                    onValueChange = {
                        errorState.value = false
                        textState.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
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
                Spacer(Modifier.size(4.dp))
                Divider(thickness = 1.5.dp, color = MaterialTheme.colorScheme.secondaryContainer)
                Spacer(Modifier.size(20.dp))
                Text("Details",  fontFamily = FontFamilies.italic,)
                OutlinedTextField(
                    value = noteState.value,
                    onValueChange = {
                        noteErrorState.value = false
                        noteState.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    isError = noteErrorState.value,
                    supportingText = {
                        if (noteErrorState.value) {
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
        }
    }
}