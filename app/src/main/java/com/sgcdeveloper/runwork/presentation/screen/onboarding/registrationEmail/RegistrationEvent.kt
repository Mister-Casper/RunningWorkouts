package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

import android.net.Uri
import com.sgcdeveloper.runwork.presentation.model.GenderChipModel

sealed class RegistrationEvent {
    class UpdateEmail(val email: String) : RegistrationEvent()
    class UpdatePassword(val password: String) : RegistrationEvent()
    class UpdateConfirmPassword(val password: String) : RegistrationEvent()
    class UpdatePasswordVisibility(val isVisible: Boolean) : RegistrationEvent()
    class UpdateFirstName(val firstName: String) : RegistrationEvent()
    class UpdateLastName(val lastName: String) : RegistrationEvent()
    class UpdateGender(val genderChip: GenderChipModel) : RegistrationEvent()
    class UpdateProfilePic(val picUri: Uri) : RegistrationEvent()

    object Next : RegistrationEvent()
    object PermissionFailed : RegistrationEvent()
}