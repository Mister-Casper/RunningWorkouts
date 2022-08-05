package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

sealed class LogInEvent {
    class UpdateEmail(val email: String) : LogInEvent()
    class UpdatePassword(val password: String) : LogInEvent()

    object LogIn : LogInEvent()
    object ForgotPassword : LogInEvent()
}