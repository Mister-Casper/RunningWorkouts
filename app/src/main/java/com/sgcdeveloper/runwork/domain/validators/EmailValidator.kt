package com.sgcdeveloper.runwork.domain.validators

import android.util.Patterns
import javax.inject.Inject

class EmailValidator @Inject constructor() {
    fun isValid(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}