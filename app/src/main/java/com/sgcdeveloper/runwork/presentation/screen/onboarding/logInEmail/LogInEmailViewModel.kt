package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.domain.validators.EmailValidator
import com.sgcdeveloper.runwork.domain.validators.PasswordValidator
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInEmailViewModel @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) : ViewModel() {

    private val _logInEmailScreenState = MutableStateFlow(LogInScreenState())
    val logInEmailScreenState = _logInEmailScreenState.asStateFlow()

    private val _eventChannel = Channel<Event>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEvent(logInEvent: LogInEvent) {
        when (logInEvent) {
            is LogInEvent.UpdateEmail -> {
                updateEmail(logInEvent.email)
                clearEmailError()
            }
            is LogInEvent.UpdatePassword -> {
                updatePassword(logInEvent.password)
                clearPasswordError()
            }
            LogInEvent.LogIn -> {
                logIn()
            }
            LogInEvent.ForgotPassword -> {

            }
        }
    }

    private fun updateEmail(email: String) {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(email = email)
    }

    private fun updatePassword(password: String) {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(password = password)
    }

    private fun clearEmailError() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(emailError = null)
    }

    private fun clearPasswordError() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(passwordError = null)
    }

    private fun logIn() {
        val isEmailValid = emailValidator.isValid(_logInEmailScreenState.value.email)
        val isPasswordValid = passwordValidator.isValid(_logInEmailScreenState.value.password)
        when {
            !isEmailValid -> {
                showEmailError()
            }
            !isPasswordValid -> {
                showPasswordError()
            }
            else -> {
                loginSuccess()
            }
        }
    }

    private fun showEmailError() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            emailError = TextContainer.getTextContainer(
                R.string.onboarding_login_email_error
            )
        )
    }

    private fun showPasswordError() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            passwordError = TextContainer.getTextContainer(
                R.string.onboarding_login_password_error
            )
        )
    }

    private fun loginSuccess() {
        viewModelScope.launch {
            _eventChannel.send(Event.LogIn)
        }
    }

    data class LogInScreenState(
        val email: String = "",
        val password: String = "",
        val emailError: TextContainer? = null,
        val passwordError: TextContainer? = null,
    )

    sealed class Event {
        object LogIn : Event()
    }
}