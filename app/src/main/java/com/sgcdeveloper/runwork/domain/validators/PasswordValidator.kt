package com.sgcdeveloper.runwork.domain.validators

import javax.inject.Inject

class PasswordValidator @Inject constructor() {
    fun isValid(password: String): Boolean {
        return password.length >= PASSWORD_MIN_LENGTH
    }

    fun isValid(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    companion object {
        const val PASSWORD_MIN_LENGTH = 6
    }
}