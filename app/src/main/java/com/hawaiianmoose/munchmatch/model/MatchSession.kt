package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchSession(
    var sessionId: String = "",
    var numberOfActiveMatchers: Int = 0,
    var matcherPicks: List<UserPicks> = mutableListOf<UserPicks>(),
    var selectedList: EateryList = EateryList(),
    var selectedListId: String = "",
    var completed: Boolean = false
)