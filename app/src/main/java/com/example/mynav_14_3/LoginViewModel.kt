package com.example.mynav_14_3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

/** creator SemKaminskyi
 * 12.11.2021
 */
class LoginViewModel :ViewModel() {
    enum class  AuthentificationState{
        AUTHENCICATED, UNAUTHENCICATED, INVALID
    }

    val authentificationState = FirebaseUserLiveData().map {user ->
        if (user !=null){
            AuthentificationState.AUTHENCICATED
        }else{
            AuthentificationState.UNAUTHENCICATED
        }
    }
}