package com.sgcdeveloper.runwork.domain.validators

import javax.inject.Inject

class UserNameValidator @Inject constructor() {
    fun isValid(value: String): Boolean {
        return value.isNotBlank()
    }
}