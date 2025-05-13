package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchSession(
    var sessionId: String = "",
    var numberOfActiveMatchers: Int,
    var muncherPicks: MutableSet<UserPicks>,
    var munchers: MutableSet<UserProfile>
)