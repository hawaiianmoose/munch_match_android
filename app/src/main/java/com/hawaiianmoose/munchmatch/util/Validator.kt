package com.hawaiianmoose.munchmatch.util

import androidx.compose.runtime.MutableState

object Validator {

    fun validatePasswords(password: String, confirmPassword: String, pwErrorState: MutableState<Boolean>, pwMatchState: MutableState<Boolean>):Boolean {
        if(password.length < 8 || confirmPassword.length < 8) {
            pwErrorState.value = true
        } else if(password != confirmPassword) {
            pwMatchState.value = false
            pwErrorState.value = true
        } else {
            pwMatchState.value = true
            pwErrorState.value = false
        }

        return !pwErrorState.value
    }
}
