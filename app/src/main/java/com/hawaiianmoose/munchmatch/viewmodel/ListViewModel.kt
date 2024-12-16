package com.hawaiianmoose.munchmatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hawaiianmoose.munchmatch.data.DataStoreProvider
import com.hawaiianmoose.munchmatch.model.EateryList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    private var userProfile = DataStoreProvider.getStoredUserProfile()
    private val _eateryLists = MutableStateFlow(DataStoreProvider.getStoredLists())
    val eateryLists: StateFlow<List<EateryList>> = _eateryLists

    init {
        viewModelScope.launch {
            _eateryLists.value = DataStoreProvider.getStoredLists()
            syncListsFromNetwork()
        }
    }

    private suspend fun syncListsFromNetwork() {
        val updatedLists = DataStoreProvider.syncListsFromNetwork(userProfile.listIds)
        _eateryLists.value = updatedLists
    }

    fun addNewEateryList(eateryList: EateryList) {
        viewModelScope.launch {
            DataStoreProvider.addNewList(eateryList)
            _eateryLists.value += eateryList
        }
    }

    fun deleteEateryList(eateryList: EateryList) {
        viewModelScope.launch {
            DataStoreProvider.deleteList(eateryList)
            _eateryLists.value -= eateryList
        }
    }

    fun isNewUser(): Boolean {
        return userProfile.isNewUser
    }

    fun sawNewUserTutorial() {
        userProfile = DataStoreProvider.getStoredUserProfile()
        userProfile.isNewUser = false
        viewModelScope.launch {
            DataStoreProvider.storeUserProfile(userProfile)
        }
    }
}
