package com.hawaiianmoose.munchmatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hawaiianmoose.munchmatch.model.EateryList
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {

    fun addNewEateryList(eateryList: EateryList) {
//        viewModelScope.launch {
//            DataStoreProvider.addNewList(itemList)
//            _itemLists.value += itemList
//        }
    }
}