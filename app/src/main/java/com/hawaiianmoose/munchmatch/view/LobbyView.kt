package com.hawaiianmoose.munchmatch.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.MatchSession
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.control.GreenButton
import com.hawaiianmoose.munchmatch.view.control.LobbyButton
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun LobbyView(selectedList: EateryList, navigator: NavHostController) {
    val lazyListState = rememberLazyListState()

    val testJsonMatchSession = Json.encodeToString( //TESTING get this from DB if joining otherwise create an empty one like this
        MatchSession(
            sessionId = "123",
            muncherPicks = mutableSetOf(),
            numberOfActiveMatchers = 2,
            munchers = mutableSetOf()
        )
    )

    Column(modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = CenterHorizontally) {
        Spacer(Modifier.weight(1f))
        Text("Munchers Matching...", fontSize = 28.sp)
        Spacer(Modifier.size(16.dp))
        LazyColumn(
            horizontalAlignment = CenterHorizontally,
            state = lazyListState,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
        ) {
            items(items = selectedList.sharedUsers) { item ->
                Text(item)
            }
        }
        Spacer(Modifier.weight(5f))
        LobbyButton (label = "Notify Munchers") {
            //Send Notification to List Users
        }
        Spacer(Modifier.weight(0.5f))

        if (Json.decodeFromString<MatchSession>(testJsonMatchSession).numberOfActiveMatchers > 1) {
            GreenButton(label = "Make Your Picks!") {
                navigator.navigate("match/$testJsonMatchSession")
            }
        } else {
            GreenButton(label = "Get Results!") {
                navigator.navigate("results/$testJsonMatchSession")
            }
        }
        Spacer(Modifier.weight(0.5f))
    }
}

@Composable
@Preview
fun LobbyPreview() {
    MunchMatchTheme {
        val eateryList = EateryList(
            sharedUsers = listOf(
                "(You) James D's picks are in!",
                "Mike M is still matching...",
                "Laura H is still matching...",
                "Kristie B's picks are in!",
                "Dave M has not joined."
            )
        )
        LobbyView(eateryList, NavHostController(LocalContext.current))
    }
}