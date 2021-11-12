package com.example.mynav_14_3

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/** creator SemKaminskyi
 * 12.11.2021
 */
class FirebaseUserLiveData: LiveData<FirebaseUser>() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val authStateListener = FirebaseAuth.AuthStateListener {
        firebaseAuth -> value = firebaseAuth.currentUser
    }

    override fun onActive() {
        firebaseAuth.addAuthStateListener (authStateListener)
    }

    override fun onInactive() {
        firebaseAuth.removeAuthStateListener (authStateListener)
    }
}