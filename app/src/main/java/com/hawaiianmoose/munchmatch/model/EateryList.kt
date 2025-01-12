package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class EateryList(
    val listId: String = "",
    var listName: String = "",
    var eateries: List<Eatery> = emptyList(),
    var isSortedAlphabetically: Boolean = true,
    var lastUpdated: Long = 0L,
    val sharedUsers: List<String> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as EateryList

        if (listId != other.listId) return false
        if (listName != other.listName) return false
        if (isSortedAlphabetically != other.isSortedAlphabetically) return false
        if (lastUpdated != other.lastUpdated) return false
        if (eateries !=(other.eateries)) return false
        if (sharedUsers != other.sharedUsers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = listId.hashCode()
        result = 31 * result + listName.hashCode()
        result = 31 * result + isSortedAlphabetically.hashCode()
        result = 31 * result + lastUpdated.hashCode()
        result = 31 * result + eateries.hashCode()
        result = 31 * result + sharedUsers.hashCode()
        return result
    }
}