package com.sgcdeveloper.runwork.domain.validators

import javax.inject.Inject

class EmailValidator @Inject constructor() {
    fun isValid(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}