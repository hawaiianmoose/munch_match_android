package com.hawaiianmoose.munchmatch.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val userName: String = "",
    val userEmail: String = "",
    val userImageUrl: String = "",
    var listIds: List<String> = emptyList(),
    var isNewUser: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UserProfile

        if (userName != other.userName) return false
        if (userEmail != other.userEmail) return false
        if (userImageUrl != other.userImageUrl) return false
        if (listIds != (other.listIds)) return false
        if (isNewUser != other.isNewUser) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userEmail.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + userImageUrl.hashCode()
        result = 31 * result + listIds.hashCode()
        result = 31 * result + isNewUser.hashCode()
        return result
    }
}
