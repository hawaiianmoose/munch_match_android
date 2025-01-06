package com.hawaiianmoose.munchmatch.util

import java.time.Instant
import kotlin.random.Random

object IdGenerator {
    fun generateUniqueId(): String {
        val epochSeconds = Instant.now().epochSecond
        val randomSuffix = Random.nextInt(1000, 9999)
        return "$epochSeconds-$randomSuffix"
    }
}
