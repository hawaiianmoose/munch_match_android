package com.hawaiianmoose.munchmatch.view

import android.content.Intent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.R
import com.hawaiianmoose.munchmatch.model.Eatery
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.network.EateryListClient
import com.hawaiianmoose.munchmatch.ui.theme.FontFamilies
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.control.ListMenu
import com.hawaiianmoose.munchmatch.view.control.SwipeBackground
import com.hawaiianmoose.munchmatch.view.control.noRippleClickable
import com.hawaiianmoose.munchmatch.view.dialog.AddItemModal
import com.hawaiianmoose.munchmatch.view.dialog.EditItemModal
import com.hawaiianmoose.munchmatch.view.dialog.EditListModal
import com.hawaiianmoose.munchmatch.viewmodel.ListDetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListDetailView(selectedList: EateryList, navigator: NavHostController) {
    val localContext = LocalContext.current
    val listDetailViewModel = remember { ListDetailViewModel(EateryListClient, selectedList) }
    val itemsState by listDetailViewModel.itemsFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isSyncing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isSyncing,
        onRefresh = {
            isSyncing = true
            coroutineScope.launch {
                delay(2500) // Simulating sync delay
                listDetailViewModel.addNewItem(Eatery("Synced Item ${itemsState.count()}", "new restaurant"))
                isSyncing = false

            //TODO run when sync fails
//                scaffoldState.snackbarHostState.showSnackbar(
//                    message = "Oops, we couldn't sync your list.",  actionLabel = "Dismiss"
//                )
            }
        }
    )

    //TODO separate list for completed items
    val alphabeticalComparator = Comparator<Eatery> { left, right ->
        left.name.compareTo(right.name, ignoreCase = true)
    }
    val alphaComparator by remember { mutableStateOf(alphabeticalComparator) }
    var showEditListModalTransitionDialog by remember { mutableStateOf(false) }
    //var showCollaboratorModalTransitionDialog by remember { mutableStateOf(false) }
    var showEditItemModalTransitionDialog by remember { mutableStateOf(false) }
    var showAddItemModalTransitionDialog by remember { mutableStateOf(false) }

    val sharedText = "Join my list on Munch Match: https://munchmatch.hawaiianmoose.com/invite?listId=12345\""
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, sharedText)
        putExtra(Intent.EXTRA_TITLE, "Share your " + selectedList.listName + " list with others!")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

//    AddCollaboratorsModal(
//        listDetailViewModel.currentList.value.sharedUsers,
//        onDismissRequest = { showCollaboratorModalTransitionDialog = false },
//        showModalTransitionDialog = showCollaboratorModalTransitionDialog
//    )
    EditListModal(
        listDetailViewModel,
        onDismissRequest = { showEditListModalTransitionDialog = false },
        showModalTransitionDialog = showEditListModalTransitionDialog
    )
    EditItemModal(
        listDetailViewModel,
        onDismissRequest = { showEditItemModalTransitionDialog = false },
        showModalTransitionDialog = showEditItemModalTransitionDialog
    )
    AddItemModal(
        listDetailViewModel,
        itemsState,
        onDismissRequest = { showAddItemModalTransitionDialog = false },
        showModalTransitionDialog = showAddItemModalTransitionDialog
    )

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = Color.White,
                    snackbarData = data
                )
            }},
        topBar = { Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(160.dp)
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.primary),
                content = {
                    Column(
                        modifier = Modifier
                            .align(CenterStart)
                            .padding(top = 24.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Row(Modifier.offset(y = 12.dp)) {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.round_back_arrow),
                                    "Back",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .offset(x = (-10).dp)
                                        .noRippleClickable {
                                            navigator.navigate("listhome")
                                        }
                                )
                            }
                            Text(
                                text = stringResource(R.string.my_munch_lists),
                                color = Color.White,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .align(CenterVertically)
                                    .offset(x = (-18).dp, y = (-2).dp)
                            )
                            Spacer(Modifier.weight(1f))
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.add_collaborator),
                                    "Collaborators",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .noRippleClickable {
                                            //showCollaboratorModalTransitionDialog = true
                                            localContext.startActivity(shareIntent)
                                        }
                                        .size(28.dp)
                                )
                            }
                        }
                        Row(
                            Modifier.padding(start = 8.dp).offset(y = 12.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = listDetailViewModel.currentList.value.listName,
                                color = Color.White,
                                fontSize = 22.sp
                            )
                            IconButton(
                                onClick = {},
                                modifier = Modifier.offset(x = (-8).dp, y = (-8).dp)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.edit_icon),
                                    "Edit List",
                                    tint = Color.White,
                                    modifier = Modifier.noRippleClickable {
                                        showEditListModalTransitionDialog = true
                                    }
                                )
                            }
                        }
                        Row(
                            Modifier.padding(start = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${itemsState.count()} Eateries",
                                color = Color.White,
                                fontSize = 11.sp,
                            )
                            Spacer(Modifier.weight(1f))
                            ListMenu(Modifier.offset(y = (-8).dp)) { listDetailViewModel.clearAllItems() }
                        }
                    }
                }
            )
        },
//        bottomBar = { listDetailViewModel.adadaptedComposable.ZoneView() },
        backgroundColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)) {
            if (itemsState.isEmpty()) {
                EmptyListState()
            } else {
                ListContent(
                    items = getSortedItems(itemsState, listDetailViewModel, alphaComparator),
                    lazyListState = lazyListState,
                    listDetailViewModel = listDetailViewModel,
                    onShowEditItemDialogChange = { newValue ->
                        showEditItemModalTransitionDialog = newValue
                    }
                )
            }

            PullRefreshIndicator(
                refreshing = isSyncing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = MaterialTheme.colorScheme.primary,
            )

            FloatingActionButton(onClick = { showAddItemModalTransitionDialog = true },
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer, modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 80.dp, end = 24.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.plus),
                    "Add Items",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun EmptyListState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.arch_empty),
            contentDescription = "Arch Empty Image"
        )
        Spacer(Modifier.size(4.dp))
        Text(text = stringResource(R.string.zero_items),
            modifier = Modifier.align(CenterHorizontally),
            fontFamily = FontFamilies.italic,
            fontSize = 14.sp,)
    }
}

@Composable
private fun ListContent(
    items: List<Eatery>,
    lazyListState: LazyListState,
    listDetailViewModel: ListDetailViewModel,
    onShowEditItemDialogChange: (Boolean) -> Unit
) {
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
    ) {
        items(items = items, key = { it.name }) { item ->
            ItemRow(
                item = item,
                listDetailViewModel = listDetailViewModel,
                onShowEditItemDialogChange = onShowEditItemDialogChange
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemRow(
    item: Eatery,
    listDetailViewModel: ListDetailViewModel,
    onShowEditItemDialogChange: (Boolean) -> Unit,
) {
    val currentItem = remember { mutableStateOf(item) }

    SwipeToDismiss(
        state = rememberDismissState(
            confirmStateChange = {
                if (it == DismissValue.DismissedToStart) {
                    listDetailViewModel.removeItem(currentItem.value)
                    true
                } else false
            }
        ),
        modifier = Modifier.padding(vertical = 1.dp),
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = {
            FractionalThreshold(0.35f)
        },
        background = {
            SwipeBackground(
                rememberDismissState()
            )
        },
        dismissContent = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, end = 6.dp, top = 2.dp, bottom = 2.dp)
                    .height(54.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onLongPress = {
                            listDetailViewModel.setCurrentItem(currentItem.value)
                            onShowEditItemDialogChange(true)
                        }, onTap = {
//                            handleTap(
//                                item = currentItem.value,
//                                isChecked = isChecked,
//                                itemsState = listDetailViewModel.itemsFlow.value,
//                                listDetailViewModel = listDetailViewModel
//                            )
                        })
                    },
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    bottomStart = 4.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp,
                ),
                elevation = 2.dp,
            ) {
                CardRowItem(
                    item = currentItem.value,
                    listDetailViewModel = listDetailViewModel,
                    onShowEditItemDialogChange = onShowEditItemDialogChange
                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun CardRowItem(
    item: Eatery,
    listDetailViewModel: ListDetailViewModel,
    onShowEditItemDialogChange: (Boolean) -> Unit
) {
    Row(Modifier.padding(4.dp), verticalAlignment = CenterVertically) {
        Spacer(Modifier.size(24.dp))
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = CenterStart
        ) {
            Column(
                horizontalAlignment = Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.name,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    //ontStyle = if (item.isRecentlySynced) FontStyle.Italic else FontStyle.Normal, //TODO recently sync'd should not be a field
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(2.dp))
                if (item.details.isNotEmpty()) {
                    Text(
                        item.details,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }
            }
//            Icon(
//                painter = rememberAnimatedVectorPainter(
//                    animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.scratch_off_animation),
//                    atEnd = isChecked.value
//                ),
//                contentDescription = null,
//                tint = if (isChecked.value) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary,
//                modifier = Modifier
//                    .offset(x = (-18).dp)
//                    .width(164.dp)
//                    .height(26.dp)
//            )
        }
        //Spacer(Modifier.size(16.dp))
        //CardRowItemQuantityControl(item, isChecked, listDetailViewModel, onShowEditItemDialogChange)
    }
}

@Composable
fun CardRowItemCheckBox(isChecked: MutableState<Boolean>) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .size(26.dp)
            .border(
                width = 2.5.dp,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = RoundedCornerShape(4.dp)
            )
            .background(
                color = if (isChecked.value) MaterialTheme.colorScheme.tertiaryContainer else Color.White,
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Center
    ) {
        if (isChecked.value) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.round_check),
                contentDescription = "Check Box",
                tint = Color.White
            )
        }
    }
}

@Composable
fun CardRowItemQuantityControl(
    item: Eatery,
    isChecked: MutableState<Boolean>,
    listDetailViewModel: ListDetailViewModel,
    onShowEditItemDialogChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Spacer(Modifier.size(12.dp))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.forward_arrow),
            contentDescription = "Edit Item",
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    listDetailViewModel.setCurrentItem(item)
                    onShowEditItemDialogChange(true)
                },
            tint = if (isChecked.value) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.primary
        )
    }
}

//private fun handleTap(
//    item: Item,
//    isChecked: MutableState<Boolean>,
//    itemsState: MutableList<Item>,
//    listDetailViewModel: ListDetailViewModel
//) {
//    isChecked.value = !isChecked.value
//    item.isChecked = isChecked.value
//    itemsState.remove(item)
//    itemsState.add(item)
//    listDetailViewModel.updateListItems(itemsState)
//}

private fun getSortedItems(itemsState: List<Eatery>, viewModel: ListDetailViewModel, alphaComparator: Comparator<Eatery>): List<Eatery> {
    val currentList = viewModel.currentList.value
    return when {
        currentList.isSortedAlphabetically ->
            itemsState.sortedWith(alphaComparator)
        else -> itemsState
    }
}

@Preview
@Composable
fun ListViewViewPreview() {
    MunchMatchTheme {
        ListDetailView(EateryList(), NavHostController(LocalContext.current))
    }
}