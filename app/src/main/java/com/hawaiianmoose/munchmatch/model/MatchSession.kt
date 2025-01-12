package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchSession(
    var sessionId: String = "",
    var numberOfMatchers: Int,
    var matchedEateries: Map<String, Int>
)