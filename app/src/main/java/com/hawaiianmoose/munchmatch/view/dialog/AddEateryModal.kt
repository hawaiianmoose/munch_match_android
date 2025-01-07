package com.hawaiianmoose.munchmatch.view.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.model.Eatery
import com.hawaiianmoose.munchmatch.model.EaterySuggestion
import com.hawaiianmoose.munchmatch.view.control.noRippleClickable
import com.hawaiianmoose.munchmatch.viewmodel.ListDetailViewModel

@Composable
fun AddEateryModal(
    listDetailViewModel: ListDetailViewModel,
    itemsState: List<Eatery>,
    onDismissRequest: () -> Unit,
    showModalTransitionDialog: Boolean
) {
    if (showModalTransitionDialog) {
        AddEateryModalTransitionDialog(onDismissRequest, listDetailViewModel, itemsState)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEateryModalTransitionDialog(
    onDismissRequest: () -> Unit,
    listDetailViewModel: ListDetailViewModel,
    itemsState: List<Eatery>
) {
    val addEateryTextState = remember { mutableStateOf(TextFieldValue()) }
    val suggestionsState = listDetailViewModel.suggestionsFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    var queryChanged by remember { mutableStateOf(false) }
    val suggestionsList = remember {
        mutableStateListOf<EaterySuggestion>().apply {
            suggestionsState.value.forEach {
                add(EaterySuggestion(it.name, it.details, false))
            }
        }
    }

    val sponsorComparator = Comparator<EaterySuggestion> { left, right ->
        right.isSponsored.compareTo(left.isSponsored)
    }
    val sponsoredComparator by remember { mutableStateOf(sponsorComparator) }

    //TODO TESTING AD ADDITION THIS NEEDS ATTENTION ALSO FOR KEYWORD
    if(!suggestionsList.any { i -> i.name == "Applebee's"} && (!itemsState.any { i -> i.name == "Applebee's" })) { //ads need to only be added once
        suggestionsList.add(EaterySuggestion("Applebee's", "", true))
    }
    //TODO add ad items to suggestion list

    val sortedSuggestions = remember {mutableStateOf(suggestionsList.sortedWith(sponsoredComparator))}

    fun filterSuggestions(filterString: String) {
        sortedSuggestions.value = suggestionsList.filter { s -> s.name.startsWith(filterString, ignoreCase = true) }
    }

    fun addItem(itemName: String, fromSuggestion: Boolean = false) {
        val itemToAdd = Eatery(name = itemName, "")
        if(fromSuggestion) {
            suggestionsList.removeAll { s -> s.name == itemName }
            sortedSuggestions.value = suggestionsList.sortedWith(sponsoredComparator)
        } else {
            addEateryTextState.value = TextFieldValue()
        }
        if(itemsState.any { item -> item.name == itemToAdd.name || itemToAdd.name.isBlank() }) { return } //silently prevent duplicates and blanks
        listDetailViewModel.addNewItem(itemToAdd)
    }

    ModalTransitionDialog(onDismissRequest = onDismissRequest) { modalTransitionDialogHelper ->

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        Scaffold(topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(154.dp)
                    .background(Color.Transparent)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 28.dp, 28.dp))
                    .background(MaterialTheme.colorScheme.primary), content = {
                    Text(
                        text = stringResource(R.string.add_items),
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 74.dp)
                    )
                    TextButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(end = 12.dp, bottom = 72.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        Text(stringResource(R.string.done),
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.noRippleClickable {
                                modalTransitionDialogHelper.triggerAnimatedClose()
                            })
                    }
                    TextButton(modifier = Modifier
                        .padding(start = 12.dp, bottom = 72.dp)
                        .align(Alignment.CenterStart),
                        onClick = {}) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.round_back_arrow),
                            "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .size(16.dp)
                                .offset(x = (-10).dp)
                                .noRippleClickable {
                                    modalTransitionDialogHelper.triggerAnimatedClose()
                                })
                        Text(stringResource(R.string.list),
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.noRippleClickable {
                                modalTransitionDialogHelper.triggerAnimatedClose()
                            })
                    }

                    OutlinedTextField(value = addEateryTextState.value,
                        onValueChange = {
                            addEateryTextState.value = it
                            queryChanged = true
                            filterSuggestions(it.text)
                                        },
                        supportingText = {/*for spacing only*/},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .focusRequester(focusRequester)
                            .padding(start = 16.dp, end = 16.dp),
                        textStyle = MaterialTheme.typography.titleMedium,
                        placeholder = {
                            Text(
                                stringResource(R.string.add_items_placeholder), style = MaterialTheme.typography.labelLarge, color = Color.White
                            )
                        },
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedPlaceholderColor = Color.White,
                            unfocusedPlaceholderColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            errorBorderColor = MaterialTheme.colorScheme.error,
                            errorLabelColor = Color.White,
                            cursorColor = Color.White
                        ),
                        shape = RoundedCornerShape(32.dp),
                        keyboardActions = KeyboardActions(onDone = { addItem(addEateryTextState.value.text) }),
                        trailingIcon = {
                            OutlinedIconButton(
                                onClick = {},
                                modifier = Modifier.padding(end = 4.dp),
                                border = BorderStroke(0.dp, MaterialTheme.colorScheme.primary),
                                colors = IconButtonDefaults.outlinedIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Icon(imageVector = Icons.Filled.Add,
                                    "Add Eatery",
                                    tint = Color.White,
                                    modifier = Modifier.noRippleClickable { addItem(addEateryTextState.value.text) })
                            }
                        })
                })
            }
        }, bottomBar = {
           // nothing?
        }, backgroundColor = MaterialTheme.colorScheme.onBackground
        ) { paddingValues ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)) {
                if (!queryChanged) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.suggesteditems),
                        contentDescription = "Suggested Eateries",
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 16.dp)
                    )
                }
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.onBackground)
                        .padding(start = 8.dp, end = 8.dp, top = 12.dp)
                ) {
                    items(items = sortedSuggestions.value, key = { s -> s.name}, itemContent = { suggestion ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
                                .height(54.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = { addItem(suggestion.name, true) }
                                    )
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                bottomStart = 4.dp,
                                topEnd = 4.dp,
                                bottomEnd = 4.dp,
                            )
                        ) {
                            Row(
                                Modifier.padding(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.offset(x = (-6).dp)
                                ) {
                                    Icon(imageVector = if (suggestion.isSponsored) {
                                        ImageVector.vectorResource(id = R.drawable.add_circle) }
                                    else { ImageVector.vectorResource(id = R.drawable.readd) },
                                        "Add Eatery",
                                        tint = MaterialTheme.colorScheme.tertiaryContainer,
                                        modifier = Modifier.noRippleClickable { addItem(suggestion.name, true) })
                                }
                                Text(text = suggestion.name, modifier = Modifier
                                    .padding(bottom = 2.dp)
                                    .noRippleClickable { addItem(suggestion.name, true) }
                                    .weight(2f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                if(suggestion.isSponsored) {
                                    Spacer(Modifier.size(2.dp))
                                    Chip(onClick = { addItem(suggestion.name, true) },
                                        shape = RoundedCornerShape(corner = CornerSize(6.dp)),
                                        modifier = Modifier.height(22.dp),
                                        colors = ChipDefaults.chipColors(
                                            backgroundColor = Color.Transparent
                                        ),
                                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)) {
                                        Text(text = stringResource(id = R.string.ad), fontSize = 13.sp, modifier = Modifier.padding(bottom = 2.dp))
                                    }
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}