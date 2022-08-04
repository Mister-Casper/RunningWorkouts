package com.sgcdeveloper.runwork.presentation.screen.onboarding

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

sealed class AuthEvent {
    data class LogInByGoogle(val account: GoogleSignInAccount) : AuthEvent()

    object LogInByGoogleFailed : AuthEvent()
    object SignOut : AuthEvent()

    object GoLogInByEmail : AuthEvent()
}