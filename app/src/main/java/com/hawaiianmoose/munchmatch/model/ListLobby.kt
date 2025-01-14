package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class ListLobby(
    var hostUser: String,
    var lobbyUsers: Map<String, Boolean>,
)