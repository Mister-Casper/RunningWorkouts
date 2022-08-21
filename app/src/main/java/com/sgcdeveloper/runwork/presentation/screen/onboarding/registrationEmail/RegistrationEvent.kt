package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

sealed class RegistrationEvent {
    class UpdateEmail(val email: String) : RegistrationEvent()
    class UpdatePassword(val password: String) : RegistrationEvent()
    class UpdatePasswordVisibility(val isVisible: Boolean) : RegistrationEvent()

    object BackPressed : RegistrationEvent()
    object Action : RegistrationEvent()
    object ForgotPassword : RegistrationEvent()
}