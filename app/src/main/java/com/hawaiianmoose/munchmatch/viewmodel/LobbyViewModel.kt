package com.hawaiianmoose.munchmatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hawaiianmoose.munchmatch.data.DataStoreProvider
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.UserProfile
import kotlinx.coroutines.launch

class LobbyViewModel: ViewModel() {
    fun initializeMatchingSession(userProfile: UserProfile, selectedList: EateryList) {
        viewModelScope.launch {
            DataStoreProvider.getOrCreateMatchSession(userProfile, selectedList)
        }
    }
}