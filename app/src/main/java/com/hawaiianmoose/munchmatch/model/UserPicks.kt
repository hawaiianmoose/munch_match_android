package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPicks(
    var user: UserProfile,
    var userPicks: Map<String, Boolean>
)
