package com.hawaiianmoose.munchmatch.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.MatchSession
import com.hawaiianmoose.munchmatch.model.UserPicks
import com.hawaiianmoose.munchmatch.model.UserProfile

object MatchSessionClient {

    fun getOrCreateMatchSession(
        userProfile: UserProfile,
        eateryList: EateryList,
        onResult: (MatchSession?) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val matchSessionsRef = db.collection("matchSessions")

        // Step 1: Query for existing session with this list ID
        matchSessionsRef
            .whereEqualTo("selectedListId", eateryList.listId)
            .whereEqualTo("completed", false)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Session exists, return it
                    val existingSession = querySnapshot.documents[0].toObject(MatchSession::class.java)
                    existingSession?.sessionId = querySnapshot.documents[0].id
                    onResult(existingSession)
                } else {
                    // Step 2: Create a new session
                    val newDocRef = matchSessionsRef.document()
                    val sessionId = newDocRef.id

                    val newSession = MatchSession(
                        sessionId = sessionId,
                        numberOfActiveMatchers = 1,
                        matcherPicks = mutableListOf(
                            UserPicks(
                                user = userProfile,
                                userPicks = mapOf()
                            )
                        ),
                        selectedList = eateryList,
                        selectedListId = eateryList.listId,
                        completed = false
                    )

                    newDocRef.set(newSession)
                        .addOnSuccessListener {
                            Log.d("Firestore", "MatchSession created with ID: $sessionId")
                            onResult(newSession)
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error creating matchSession", e)
                            onResult(null)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error querying matchSessions", e)
                onResult(null)
            }
    }
}