package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

sealed class LogInEvent {
    class UpdateEmail(val email: String) : LogInEvent()
    class UpdatePassword(val password: String) : LogInEvent()
    class UpdatePasswordVisibility(val isVisible: Boolean) : LogInEvent()

    object BackPressed : LogInEvent()
    object Action : LogInEvent()
    object ForgotPassword : LogInEvent()
}