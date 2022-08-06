package com.sgcdeveloper.runwork.presentation.screen.onboarding

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

sealed class AuthEvent {
    data class LogInByGoogle(val account: GoogleSignInAccount) : AuthEvent()
    data class LogInByGoogleFailed(val error: Throwable) : AuthEvent()

    object SignOut : AuthEvent()

    object GoLogInByEmail : AuthEvent()
}