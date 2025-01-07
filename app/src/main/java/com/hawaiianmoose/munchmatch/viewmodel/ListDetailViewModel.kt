package com.hawaiianmoose.munchmatch.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hawaiianmoose.munchmatch.data.DataStoreProvider
import com.hawaiianmoose.munchmatch.model.Eatery
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.EaterySuggestion
import com.hawaiianmoose.munchmatch.network.EateryListClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant

class ListDetailViewModel(private val eateryListClient: EateryListClient, list: EateryList) : ViewModel() {
    var currentList: MutableState<EateryList> = mutableStateOf(list)
    var currentEatery: MutableState<Eatery> = mutableStateOf(Eatery("", ""))
    private var currentListItems = mutableStateListOf<Eatery>().apply {
            currentList.value.eateries.forEach {
                add(it)
            }
        }
    private val _itemsFlow = MutableStateFlow(currentListItems)
    val itemsFlow: StateFlow<MutableList<Eatery>> get() = _itemsFlow

    private var currentSuggestions = mutableStateListOf<EaterySuggestion>().apply {
        //TODO get suggested eateries and insert them here
//        currentList.value.eateries.forEach {
//            add(it)
//        }
    }
    private val _suggestionsFlow = MutableStateFlow(currentSuggestions)
    val suggestionsFlow: StateFlow<MutableList<EaterySuggestion>> get() = _suggestionsFlow

    fun addNewItem(item: Eatery) {
        updateLastUpdated()
        currentListItems.add(item)
        currentList.value.eateries = currentListItems
        viewModelScope.launch {
            DataStoreProvider.updateList(currentList.value)
        }
    }

    fun removeItem(item: Eatery) {
        updateLastUpdated()
        currentListItems.remove(item)
        currentList.value.eateries = currentListItems
        viewModelScope.launch {
            DataStoreProvider.updateList(currentList.value)
        }
    }

    fun clearAllItems() {
        updateLastUpdated()
        currentListItems.removeAll { true }
        currentList.value.eateries = currentListItems
        viewModelScope.launch {
            DataStoreProvider.updateList(currentList.value)
        }
    }

    fun updateListItems(items: List<Eatery>) {
        updateLastUpdated()
        currentList.value.eateries = items
        viewModelScope.launch {
            DataStoreProvider.updateList(currentList.value)
        }
    }

    fun updateItemList(listName: String) {
        updateLastUpdated()
        currentList.value.listName = listName
        viewModelScope.launch {
            DataStoreProvider.updateList(currentList.value)
        }
    }

    fun setCurrentItem(item: Eatery) {
        currentEatery.value = item
    }

    fun updateItem(name: String, notes: String) {
        updateLastUpdated()
        val updatedItem = Eatery(name, notes)
        if (updatedItem.name == currentEatery.value.name) {
            val itemToUpdate = currentListItems[currentListItems.indexOf(currentEatery.value)]
            itemToUpdate.name = name
        } else {
            currentListItems.remove(currentEatery.value)
            currentListItems.add(updatedItem)
        }
        currentEatery.value = updatedItem
        currentList.value.eateries = currentListItems
        viewModelScope.launch {
            DataStoreProvider.updateList(currentList.value)
        }
    }

    fun getUpdatedItemList(listId: String) { //TODO sync pull?
        //updateLastUpdated() only if there are actual updates pulled down
        viewModelScope.launch {
            currentList.value = eateryListClient.getUpdatedList(listId)
        }
    }

    private fun updateLastUpdated() {
        currentList.value.lastUpdated = Instant.now().epochSecond
    }
}