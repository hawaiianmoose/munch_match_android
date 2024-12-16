package com.hawaiianmoose.munchmatch.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.util.LastUpdatedConverter
import com.hawaiianmoose.munchmatch.view.control.ArchFooter
import com.hawaiianmoose.munchmatch.view.control.CollaboratorBadge
import com.hawaiianmoose.munchmatch.view.control.EmptyListPlaceholder
import com.hawaiianmoose.munchmatch.view.control.ListHeader
import com.hawaiianmoose.munchmatch.view.control.SwipeActions
import com.hawaiianmoose.munchmatch.view.control.SwipeActionsConfig
import com.hawaiianmoose.munchmatch.viewmodel.ListViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListHomeView(navigator: NavHostController, listViewModel: ListViewModel = viewModel()) {
    val eateryListState by listViewModel.eateryLists.collectAsState()
    val navigateToAccountSettings: () -> Unit = {
        //navigator.navigate(AccountSettingsViewDestination()) TODO NAV
    }

    Scaffold(
        topBar = { ListHeader(listViewModel, navigateToAccountSettings) },
        bottomBar = { ArchFooter() },
        backgroundColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        Box(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding() - 32.dp)) {

            if(eateryListState.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)) {
                    EmptyListPlaceholder()
                }
            }

            LazyColumn(modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onBackground)
                .padding(start = 8.dp, end = 8.dp)) {
                itemsIndexed(eateryListState, key = { _, list -> list.listId}) {
                        index, list ->
                    SwipeActions(
                        startActionsConfig = SwipeActionsConfig(
                            threshold = 0.5f,
                            background = Color(0xFFECEC2B),
                            iconTint = Color.Transparent,
                            stayDismissed = false,
                            onDismiss = {}
                        ),
                        endActionsConfig = SwipeActionsConfig(
                            threshold = 0.5f,
                            background = Color.White,
                            iconTint = MaterialTheme.colorScheme.error,
                            icon = Icons.Default.Delete,
                            stayDismissed = true,
                            onDismiss = {
                                listViewModel.deleteEateryList(list)
                            }
                        ),
                        showTutorial = listViewModel.isNewUser() && index == 0,
                        isDeleteOnly = true
                    ) { state ->
                        val animateCorners by remember {
                            derivedStateOf {
                                state.offset.value.absoluteValue > 30
                            }
                        }
                        val startCorners by animateDpAsState(
                            targetValue = when {
                                state.dismissDirection == DismissDirection.StartToEnd &&
                                        animateCorners -> 8.dp
                                else -> 4.dp
                            }
                        )
                        val endCorners by animateDpAsState(
                            targetValue = when {
                                state.dismissDirection == DismissDirection.EndToStart &&
                                        animateCorners -> 8.dp
                                else -> 4.dp
                            }
                        )
                        val elevation by animateDpAsState(
                            targetValue = when {
                                animateCorners -> 8.dp
                                else -> 4.dp
                            }
                        )
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .clickable {
                                    if (listViewModel.isNewUser()) {
                                        listViewModel.sawNewUserTutorial()
                                    }
                                    //navigator.navigate(ListDetailViewDestination(list)) TODO NAV
                                },
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(
                                topStart = startCorners,
                                bottomStart = startCorners,
                                topEnd = endCorners,
                                bottomEnd = endCorners,
                            ),
                            elevation = elevation,
                        ) {
                            Box(Modifier.padding(12.dp)) {
                                Row(
                                    Modifier
                                        .align(Alignment.TopStart)
                                        .padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center) {
                                    Column(
                                        Modifier
                                            .weight(1f)
                                            .padding(end = 2.dp)) {
                                        Text(
                                            list.listName,
                                            fontSize = 18.sp,
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis,
                                            softWrap = true,
                                            modifier = Modifier.offset(y = (-4).dp)
                                        )
                                        Text(
                                            text = LastUpdatedConverter.convertTimestampToLastUpdatedString(list.lastUpdated),
                                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                            color = MaterialTheme.typography.displaySmall.color,
                                            modifier = Modifier
                                                .padding(start = 1.dp, bottom = 4.dp)
                                                .offset(y = (-4).dp)
                                        )
                                    }
                                    CollaboratorBadge(numberOfCollaborators = list.sharedUsers.count())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ListHostPreview() {
    MunchMatchTheme {
        ListHomeView(NavHostController(LocalContext.current))
    }
}
