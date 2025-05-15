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
import com.hawaiianmoose.munchmatch.model.MatchSession
import com.hawaiianmoose.munchmatch.view.control.MatchViewer

@Composable
fun MatchingView(matchSession: MatchSession, navigator: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val sampleTexts = matchSession.selectedList.eateries.map { eatery -> eatery.name }

        MatchViewer(strings = sampleTexts)
    }
}