package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

import com.google.firebase.auth.FirebaseAuth
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.data.model.user.UserGender
import com.sgcdeveloper.runwork.data.model.user.UserInfo
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import com.sgcdeveloper.runwork.domain.validators.ConfirmPasswordValidator
import com.sgcdeveloper.runwork.domain.validators.EmailValidator
import com.sgcdeveloper.runwork.domain.validators.PasswordValidator
import com.sgcdeveloper.runwork.domain.validators.UserNameValidator
import com.sgcdeveloper.runwork.presentation.component.chip.controller.OneActiveItemChipsController
import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel
import com.sgcdeveloper.runwork.presentation.navigation.NavigableViewModel
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEvent
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEventsHandler
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.getTextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.toTextContainer
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
import com.sgcdeveloper.runwork.presentation.util.userGenderChips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RegistrationEmailViewModel @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val userNameValidator: UserNameValidator,
    private val confirmPasswordValidator: ConfirmPasswordValidator,
    private val firebaseAuth: FirebaseAuth,
    private val appRepository: AppRepository,
    navigationEventsHandler: NavigationEventsHandler
) : NavigableViewModel(navigationEventsHandler) {

    private val _registrationEmailScreenState = MutableStateFlow(LogInScreenState())
    val registrationEmailScreenState = _registrationEmailScreenState.asStateFlow()

    private val genderChipsController = OneActiveItemChipsController(_registrationEmailScreenState.value.genderChips)

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.Next -> {
                if (validateFields()) {
                    tryRegistration()
                }
            }
            is RegistrationEvent.UpdateEmail -> {
                updateEmail(event.email)
            }
            is RegistrationEvent.UpdateFirstName -> {
                updateFirstName(event.firstName)
            }
            is RegistrationEvent.UpdateLastName -> {
                updateLastName(event.lastName)
            }
            is RegistrationEvent.UpdatePassword -> {
                updatePassword(event.password)
            }
            is RegistrationEvent.UpdateConfirmPassword -> {
                updateConfirmPassword(event.password)
            }
            is RegistrationEvent.UpdateGender -> {
                updateSex(event.genderChip)
            }
            is RegistrationEvent.UpdatePasswordVisibility -> {
                updatePasswordVisibility(event.isVisible)
            }
        }
    }

    private fun updatePasswordVisibility(newIsVisible: Boolean) {
        _registrationEmailScreenState.update { it.copy(isPasswordVisible = newIsVisible) }
    }

    private fun updateLastName(newLastName: String) {
        _registrationEmailScreenState.update { it.copy(lastName = newLastName, firstNameError = null) }
    }

    private fun updateFirstName(newFirstName: String) {
        _registrationEmailScreenState.update { it.copy(firstName = newFirstName, firstNameError = null) }
    }

    private fun updateEmail(newEmail: String) {
        _registrationEmailScreenState.update { it.copy(email = newEmail, emailError = null) }
    }

    private fun updatePassword(newPassword: String) {
        _registrationEmailScreenState.update { it.copy(password = newPassword, passwordError = null) }
    }

    private fun updateConfirmPassword(newConfirmPassword: String) {
        _registrationEmailScreenState.update {
            it.copy(
                confirmPassword = newConfirmPassword,
                confirmPasswordError = null
            )
        }
    }

    private fun updateSex(genderChip: ChipModel) {
        genderChipsController.onChipClick(genderChip)
        _registrationEmailScreenState.update { it.copy(genderChips = genderChipsController.getAllChips()) }
    }

    private fun validateFields(): Boolean {
        with(_registrationEmailScreenState.value) {
            val isEmailValid = emailValidator.isValid(email)
            val isPasswordValid = passwordValidator.isValid(password)
            val isConfirmPasswordError = confirmPasswordValidator.isValid(password, confirmPassword)
            val isFirstNameValid = userNameValidator.isValid(firstName)
            val isLastNameValid = userNameValidator.isValid(lastName)
            when {
                !isFirstNameValid -> {
                    _registrationEmailScreenState.update { it.copy(firstNameError = getTextContainer(R.string.onboarding__registration_name_error)) }
                }
                !isLastNameValid -> {
                    _registrationEmailScreenState.update { it.copy(firstNameError = getTextContainer(R.string.onboarding__registration_name_error)) }
                }
                !isEmailValid -> {
                    _registrationEmailScreenState.update { it.copy(firstNameError = getTextContainer(R.string.onboarding__login_email_error)) }
                }
                !isPasswordValid -> {
                    _registrationEmailScreenState.update { it.copy(firstNameError = getTextContainer(R.string.onboarding__login_password_error)) }
                }
                !isConfirmPasswordError -> {
                    _registrationEmailScreenState.update { it.copy(firstNameError = getTextContainer(R.string.onboarding__registration_confirm_password_error)) }
                }
                else -> {
                    return true
                }
            }
        }
        return false
    }

    private fun tryRegistration() {
        with(_registrationEmailScreenState.value) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    registrationSucceed()
                }
                .addOnFailureListener {
                    sendEvent(NavigationEvent.ShowErrorMassage(it.getAuthErrorInfo()))
                }
        }
    }

    private fun registrationSucceed() {
        saveUserInfo()
        sendEvent(NavigationEvent.NavigateTo(GetStartedScreenDestination))
    }

    private fun saveUserInfo() {
        with(_registrationEmailScreenState.value) {
            appRepository.setUserInfo(
                UserInfo(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    profilePic = userIconPath,
                    gender = getSelectedGender(),
                )
            )
        }
    }

    private fun getSelectedGender(): UserGender {
        return genderChipsController.getActiveChipOrNull()?.data as? UserGender ?: UserGender.NOT_SAID
    }

    data class LogInScreenState(
        val userIconPath: String = "",
        val email: String = "",
        val emailError: TextContainer? = null,
        val password: String = "",
        val passwordError: TextContainer? = null,
        val confirmPassword: String = "",
        val confirmPasswordError: TextContainer? = null,
        val isPasswordVisible: Boolean = false,
        val firstName: String = "",
        val firstNameError: TextContainer? = null,
        val lastName: String = "",
        val lastNameError: TextContainer? = null,
        val genderChips: List<ChipModel> = userGenderChips
    )
}