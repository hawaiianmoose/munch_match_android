package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class Eatery(
    var name: String = "",
    var details: String = ""
)
