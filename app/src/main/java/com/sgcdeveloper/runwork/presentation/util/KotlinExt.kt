package com.sgcdeveloper.runwork.presentation.util

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.getTextContainer

fun Throwable.getAuthErrorInfo(): TextContainer {
    return when (this) {
        is FirebaseAuthWeakPasswordException -> {
            getTextContainer(R.string.onboarding_login_password_error)
        }
        is FirebaseAuthInvalidCredentialsException -> {
            getTextContainer(R.string.onboarding_login_invalid_email)
        }
        is FirebaseAuthUserCollisionException -> {
            getTextContainer(R.string.onboarding_login_already_exist)
        }
        else -> {
            TextContainer.StringTextContainer(this.localizedMessage.orEmpty())
        }
    }
}