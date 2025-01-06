package com.hawaiianmoose.munchmatch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import com.google.firebase.auth.FirebaseAuth
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.UserProfile
import com.hawaiianmoose.munchmatch.network.EateryListClient
import com.hawaiianmoose.munchmatch.network.UserProfileClient
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

object DataStoreProvider {
    private val backgroundScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val USER_PROFILE = byteArrayPreferencesKey("user_profile")
    private var isSignedIn: Boolean = false
    private lateinit var dataStore: DataStore<Preferences>
    internal const val DATA_STORE_FILE_NAME = "user.preferences_pb"

    fun createDataStore(producePath: () -> String) {
        dataStore = PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = emptyList(),
            produceFile = { producePath().toPath() }
        )
        isSignedIn = getStoredUserProfile().userEmail.isNotEmpty()
    }

    fun getStoredUserProfile(): UserProfile {
        var profile = UserProfile("", "", "", listOf())

        runBlocking {
            val profileByteArray = dataStore.data.map { preferences ->
                preferences[USER_PROFILE] ?: byteArrayOf()
            }.firstOrNull()

            if (profileByteArray?.isNotEmpty() == true) {
                profile = Json.decodeFromString(
                    UserProfile.serializer(),
                    profileByteArray.decodeToString()
                )
            }
        }
        return profile
    }

    suspend fun storeUserProfile(userProfile: UserProfile) {
        try {
            coroutineScope {
                val storeInDataStore = async {
                    dataStore.edit { preferences ->
                        preferences[USER_PROFILE] =
                            Json.encodeToString(UserProfile.serializer(), userProfile).toByteArray()
                    }
                }
                val storeInClient = async {
                    UserProfileClient.storeUserProfile(userProfile)
                }
                storeInDataStore.await()
                storeInClient.await()
                isSignedIn = true
            }
        } catch (e: Exception) {
            Log.e("ProfileStorage", "Failed to store user profile: ${e.message}")
        }
    }

    fun getStoredLists(): List<EateryList> {
        val lists = arrayListOf<EateryList>()
        runBlocking {
            for (listId in getStoredUserProfile().listIds) {
                val listByteArray = dataStore.data.map { preferences ->
                    preferences[byteArrayPreferencesKey(listId)] ?: byteArrayOf()
                }.firstOrNull()

                if (listByteArray?.isNotEmpty() == true) {
                    lists.add(
                        Json.decodeFromString(
                            EateryList.serializer(),
                            listByteArray.decodeToString()
                        )
                    )
                }
            }
        }
        return lists
    }

    suspend fun addNewList(eateryList: EateryList) {
        val currentUserProfile = getStoredUserProfile()
        val listIds = currentUserProfile.listIds.toMutableList()
        listIds.add(eateryList.listId)
        currentUserProfile.listIds = listIds
        backgroundScope.launch {
            storeUserProfile(currentUserProfile)
        }
        backgroundScope.launch {
            storeUserList(eateryList)
        }
    }

    suspend fun deleteList(eateryList: EateryList) {
        val currentUserProfile = getStoredUserProfile()
        val listIds = currentUserProfile.listIds.toMutableList()
        listIds.remove(eateryList.listId)
        currentUserProfile.listIds = listIds
        backgroundScope.launch {
            storeUserProfile(currentUserProfile)
        }
        backgroundScope.launch {
            deleteListFromDataStore(eateryList.listId)
        }
    }

    suspend fun syncListsFromNetwork(eateryListIds: List<String>): List<EateryList> {
        return withContext(Dispatchers.IO) {
            val updatedLists = EateryListClient.getEateryLists(eateryListIds)
            updatedLists.map { list -> async { storeUserListLocally(list) } }.awaitAll()
            updatedLists
        }
    }

    suspend fun updateList(eateryList: EateryList) {
        storeUserList(eateryList)
    }

    suspend fun deleteDataStore() {
        FirebaseAuth.getInstance().signOut()
        dataStore.edit { it.clear() }
        isSignedIn = false
    }

    suspend fun deleteUserProfile() {
        UserProfileClient.deleteUserProfileAndUnsharedLists(userProfile = getStoredUserProfile())
        deleteDataStore()
    }

    fun isSignedIn(): Boolean {
        return isSignedIn
    }

    private suspend fun storeUserListLocally(eateryList: EateryList) {
        dataStore.edit { preferences ->
            preferences[byteArrayPreferencesKey(eateryList.listId)] =
                Json.encodeToString(EateryList.serializer(), eateryList).toByteArray()
        }
    }

    private suspend fun storeUserList(eateryList: EateryList) {
        dataStore.edit { preferences ->
            preferences[byteArrayPreferencesKey(eateryList.listId)] =
                Json.encodeToString(EateryList.serializer(), eateryList).toByteArray()
        }
        EateryListClient.storeEateryList(eateryList)
    }

    private suspend fun deleteListFromDataStore(listId: String) {
        dataStore.edit {
            it.remove(byteArrayPreferencesKey(listId))
        }
        EateryListClient.deleteEateryList(listId)
    }
}