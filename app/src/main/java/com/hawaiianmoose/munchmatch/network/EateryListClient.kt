package com.hawaiianmoose.munchmatch.network

import com.google.firebase.firestore.FirebaseFirestore
import com.hawaiianmoose.munchmatch.model.EateryList
import kotlinx.coroutines.tasks.await

object EateryListClient {
    private var listsReference = FirebaseFirestore.getInstance().collection("eateryLists")

    suspend fun getEateryLists(listIds: List<String>): List<EateryList> {
        return try {
            val querySnapshot = listsReference
                .whereIn("listId", listIds)
                .get()
                .await()
            querySnapshot.toObjects(EateryList::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun storeEateryList(eateryList: EateryList) {
        listsReference.document(eateryList.listId).set(eateryList)
    }

    fun deleteEateryList(eateryListId: String) {
        listsReference.document(eateryListId).delete()
    }

    suspend fun getUpdatedList(listId: String): EateryList {
        return listsReference.document(listId).get().await().toObject(EateryList::class.java) ?: EateryList()
    }
}
