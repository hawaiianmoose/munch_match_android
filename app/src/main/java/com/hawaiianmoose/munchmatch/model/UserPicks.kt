package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPicks(
    var userName: String,
    var userPicks: Map<String, Boolean>
)
