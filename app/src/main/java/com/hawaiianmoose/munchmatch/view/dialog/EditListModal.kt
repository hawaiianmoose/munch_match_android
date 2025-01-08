package com.hawaiianmoose.munchmatch.view.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.control.GreenButton
import com.hawaiianmoose.munchmatch.view.control.GreenSwitch
import com.hawaiianmoose.munchmatch.view.control.ModalDialogHeader
import com.hawaiianmoose.munchmatch.view.control.SparkleFooter
import com.hawaiianmoose.munchmatch.viewmodel.ListDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun EditListModal(
    listDetailViewModel: ListDetailViewModel,
    onDismissRequest: () -> Unit,
    showModalTransitionDialog: Boolean
) {
    if (showModalTransitionDialog) {
        EditListModalTransitionDialog(onDismissRequest, listDetailViewModel)
    }
}

@Composable
fun EditListModalTransitionDialog(onDismissRequest: () -> Unit,  listDetailViewModel: ListDetailViewModel)
{
    val textState = remember { mutableStateOf(TextFieldValue(text = listDetailViewModel.currentList.value.listName)) }
    val errorState = remember { mutableStateOf(false) }
    val alphaSortState = remember { mutableStateOf(listDetailViewModel.currentList.value.isSortedAlphabetically) }

    ModalTransitionDialog(onDismissRequest = onDismissRequest) { modalTransitionDialogHelper ->
        Scaffold(
            topBar = {
                ModalDialogHeader(stringResource(R.string.edit_list), modalTransitionDialogHelper)
            },
            bottomBar = {
                Box(Modifier.fillMaxWidth().padding(bottom = 72.dp)) {
                    SparkleFooter()
                    Column(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 28.dp, start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GreenButton(label = stringResource(R.string.save), clickFunc = {
                            if (textState.value.text.isEmpty()) {
                                errorState.value = true
                            } else {
                                listDetailViewModel.updateItemList(
                                    textState.value.text.trim(),
                                    alphaSortState.value,
                                )
                                modalTransitionDialogHelper::triggerAnimatedClose.invoke()
                            }
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
                Row {
                    Text(
                        stringResource(R.string.sort_alpha),
                        Modifier
                            .weight(5f)
                            .align(Alignment.CenterVertically),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                    GreenSwitch(alphaSortState, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
@Preview
fun EditListModalPreview() {
    val textState = remember { mutableStateOf(TextFieldValue(text = "Groceries")) }
    val errorState = remember { mutableStateOf(false) }
    val onCloseSharedFlow: MutableSharedFlow<Unit> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val alphaSortState = remember { mutableStateOf(true) }

    MunchMatchTheme {
        Scaffold(
            topBar = {
                ModalDialogHeader(stringResource(R.string.edit_list), ModalTransitionDialogHelper(coroutineScope, onCloseSharedFlow))
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
                Row {
                    Text(
                        stringResource(R.string.sort_alpha),
                        Modifier
                            .weight(5f)
                            .align(Alignment.CenterVertically),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                    GreenSwitch(alphaSortState, Modifier.weight(1f))
                }
            }
        }
    }
}