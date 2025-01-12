package com.hawaiianmoose.munchmatch.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hawaiianmoose.munchmatch.model.EateryList

@Composable
fun LobbyView(selectedList: EateryList, navigator: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Lobby View",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp
        )
    }
}