package com.sgcdeveloper.runwork.domain.validators

import javax.inject.Inject

class ConfirmPasswordValidator @Inject constructor() {
    fun isValid(password: String, confirmPassword: String): Boolean {
        return password.trim() == confirmPassword.trim()
    }
}