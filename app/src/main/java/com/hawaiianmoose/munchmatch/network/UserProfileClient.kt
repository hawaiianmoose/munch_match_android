package com.hawaiianmoose.munchmatch.network

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.UserProfile

object UserProfileClient {
    private val userProfileDb = FirebaseFirestore.getInstance().collection("userProfiles")
    private val eateryDb = FirebaseFirestore.getInstance().collection("eateryLists")

    private fun fetchEateryLists(listIds: List<String>, onSuccess: (List<EateryList>) -> Unit, onFailure: (Exception) -> Unit) {
        if (listIds.isEmpty()) {
            onSuccess(emptyList())
            return
        }

        val eateryListTasks = listIds.map { eateryDb.document(it).get() }
        Tasks.whenAllSuccess<DocumentSnapshot>(eateryListTasks)
            .addOnSuccessListener { eateryListDocuments ->
                val eateryLists = eateryListDocuments.mapNotNull { it.toObject(EateryList::class.java) }
                onSuccess(eateryLists)
            }
            .addOnFailureListener { onFailure(it) }
    }

    private fun getUserProfile(
        userId: String,
        onSuccess: (UserProfile?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userProfileRef = userProfileDb.document(userId)
        userProfileRef.get()
            .addOnSuccessListener { snapshot ->
                val userProfile = snapshot.toObject(UserProfile::class.java)
                userProfile?.let {
                    onSuccess(it)
                } ?: onSuccess(null)
            }.addOnFailureListener { onFailure(it) }
    }

    fun storeUserProfile(userProfile: UserProfile) {
        userProfileDb.document(userProfile.userEmail).set(userProfile)
    }

    fun fetchOrCreateUserProfile(userEmail: String, onSuccess: (UserProfile) -> Unit) {
        getUserProfile(userEmail, { userProfile ->
            if (userProfile == null) { // No profile found, create a new one
                val newProfile = UserProfile(userEmail = userEmail, isNewUser = true)
                storeUserProfile(newProfile)
                onSuccess(newProfile)
            } else {
                onSuccess(userProfile)
            }
        }, { exception ->
            println("Failed to fetch or create user profile: ${exception.message}")
        })
    }

    fun deleteUserProfileAndUnsharedLists(userProfile: UserProfile) {
        FirebaseAuth.getInstance().currentUser?.delete()
        val userProfileRef = userProfileDb.document(userProfile.userEmail)

        userProfileRef.get().addOnSuccessListener { snapshot ->
            val listIds = snapshot.toObject(UserProfile::class.java)?.listIds ?: return@addOnSuccessListener

            fetchEateryLists(listIds, { eateryLists ->
                eateryLists.forEach { list ->
                    val eateryListRef = eateryDb.document(list.listId)
                    if (list.sharedUsers.isEmpty()) {
                        eateryListRef.delete() // Delete unshared lists
                    } else if (list.sharedUsers.contains(userProfile.userEmail)) {
                        eateryListRef.update("sharedUsers", FieldValue.arrayRemove(userProfile.userEmail))
                    }
                }
                userProfileRef.delete()
            }, { exception ->
                println("Failed to delete user profile: ${exception.message}")
            })
        }
    }
}