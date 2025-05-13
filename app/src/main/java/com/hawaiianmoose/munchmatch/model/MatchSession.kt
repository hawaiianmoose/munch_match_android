package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchSession(
    var sessionId: String = "",
    var muncherPicks: MutableSet<UserPicks>,
    var matchers: MutableSet<UserProfile>
)