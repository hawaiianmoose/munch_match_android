package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class EaterySuggestion(
    var name: String = "",
    var details: String = "",
    val isSponsored: Boolean = false
)
