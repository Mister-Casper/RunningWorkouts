package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sgcdeveloper.runwork.domain.validators.EmailValidator
import com.sgcdeveloper.runwork.domain.validators.PasswordValidator
import com.sgcdeveloper.runwork.domain.validators.UserNameValidator
import com.sgcdeveloper.runwork.presentation.component.chip.model.GenderChipModel
import com.sgcdeveloper.runwork.presentation.component.chip.controller.OneActiveItemChipsController
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.userGenderChips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RegistrationEmailViewModel @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val userNameValidator: UserNameValidator,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _registrationEmailScreenState = MutableStateFlow(LogInScreenState())
    val registrationEmailScreenState = _registrationEmailScreenState.asStateFlow()

    private val _eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    private val genderChipsController = OneActiveItemChipsController(_registrationEmailScreenState.value.genderChips)

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.Next -> {
                doRegistration()
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

    private fun updateSex(genderChip: GenderChipModel) {
        genderChipsController.onChipClick(genderChip)
        _registrationEmailScreenState.update { it.copy(genderChips = genderChipsController.getAllChips()) }
    }

    private fun doRegistration() {
        with(_registrationEmailScreenState.value) {
            val isEmailValid = emailValidator.isValid(email)
            val isPasswordValid = passwordValidator.isValid(password)
            val isFirstNameValid = userNameValidator.isValid(firstName)
            val isLastNameValid = userNameValidator.isValid(lastName)
            when {
                !isFirstNameValid -> {

                }
                !isLastNameValid -> {

                }
                !isEmailValid -> {

                }
                !isPasswordValid -> {

                }
                else -> {

                }
            }
        }
    }

    data class LogInScreenState(
        val userIconPath: String? = null,
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
        val genderChips: List<GenderChipModel> = userGenderChips
    )

    sealed class Event {
        data class Info(val infoMessage: TextContainer) : Event()
        data class Error(val errorInfo: TextContainer) : Event()

        object RegistrationSuccess : Event()
    }
}