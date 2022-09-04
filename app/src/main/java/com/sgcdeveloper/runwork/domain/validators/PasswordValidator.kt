package com.sgcdeveloper.runwork.domain.validators

import javax.inject.Inject

class PasswordValidator @Inject constructor() {
    fun isValid(value: String): Boolean {
        return value.length >= PASSWORD_MIN_LENGTH
    }

    companion object {
        const val PASSWORD_MIN_LENGTH = 6
    }
}