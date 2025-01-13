package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class ListLobby(
    var isMatchingInProgress: Boolean,
    var hostUser: String,
    var usersInLobby: Map<String, Boolean>,
)