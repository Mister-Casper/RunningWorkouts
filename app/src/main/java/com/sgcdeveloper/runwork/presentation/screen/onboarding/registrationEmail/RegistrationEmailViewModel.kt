package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.domain.validators.EmailValidator
import com.sgcdeveloper.runwork.domain.validators.PasswordValidator
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.getTextContainer
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
import com.sgcdeveloper.runwork.presentation.util.observe
import com.sgcdeveloper.runwork.presentation.util.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RegistrationEmailViewModel @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _logInEmailScreenState = MutableStateFlow(LogInScreenState())
    val logInEmailScreenState = _logInEmailScreenState.asStateFlow()

    private val _eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onEvent(registrationEvent: RegistrationEvent) {
        when (registrationEvent) {
            is RegistrationEvent.UpdateEmail -> {
                updateEmail(registrationEvent.email)
            }
            is RegistrationEvent.UpdatePassword -> {
                updatePassword(registrationEvent.password)
            }
            is RegistrationEvent.UpdatePasswordVisibility -> {
                changePasswordVisibility(registrationEvent.isVisible)
            }
            RegistrationEvent.Action -> {
                doAction()
            }
            RegistrationEvent.ForgotPassword -> {
                goToForgotPasswordScreen()
            }
            RegistrationEvent.BackPressed -> {
                onBackPressed()
            }
        }
    }

    private fun updateEmail(newEmail: String) {
        _logInEmailScreenState.update { it.copy(email = newEmail, emailError = null) }
    }

    private fun updatePassword(newPassword: String) {
        _logInEmailScreenState.update { it.copy(password = newPassword, passwordError = null) }
    }

    private fun doAction() {
        when (_logInEmailScreenState.value.loginState) {
            LoginState.LOGIN -> {
                doLogInAction()
            }
            LoginState.FORGOT_PASSWORD -> {
                doForgotPasswordAction()
            }
        }
    }

    private fun doLogInAction() {
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
                tryToLogIn()
            }
        }
    }

    private fun tryToLogIn() {
        firebaseAuth.signInWithEmailAndPassword(
            _logInEmailScreenState.value.email,
            _logInEmailScreenState.value.password
        ).observe(onSuccess = {
            sendEvent(_eventChannel, Event.LogInSuccess)
        }, onFailure = {
            sendEvent(_eventChannel, Event.Error(it.getAuthErrorInfo()))
        })
    }

    private fun doForgotPasswordAction() {
        when (emailValidator.isValid(_logInEmailScreenState.value.email)) {
            true -> {
                doSendForgotPasswordEmail()
            }
            false -> {
                showEmailError()
            }
        }
    }

    private fun doSendForgotPasswordEmail() {
        firebaseAuth.sendPasswordResetEmail(
            _logInEmailScreenState.value.email
        ).observe(onSuccess = {
            goToLogInEmailScreen()
            sendEvent(_eventChannel, Event.Info(getTextContainer(R.string.onboarding_password_email_sent)))
        }, onFailure = {
            sendEvent(_eventChannel, Event.Error(it.getAuthErrorInfo()))
        })
    }

    private fun showEmailError() {
        _logInEmailScreenState.update { it.copy(emailError = getTextContainer(R.string.onboarding_login_email_error)) }
    }

    private fun showPasswordError() {
        _logInEmailScreenState.update { it.copy(passwordError = getTextContainer(R.string.onboarding_login_password_error)) }
    }

    private fun goToForgotPasswordScreen() {
        _logInEmailScreenState.update {
            it.copy(
                loginState = LoginState.FORGOT_PASSWORD,
                emailError = null,
                passwordError = null
            )
        }
    }

    private fun goToLogInEmailScreen() {
        _logInEmailScreenState.update {
            it.copy(
                loginState = LoginState.LOGIN,
                emailError = null,
                passwordError = null
            )
        }
    }

    private fun onBackPressed() {
        when (_logInEmailScreenState.value.loginState) {
            LoginState.LOGIN -> {
                sendEvent(_eventChannel, Event.GoBack)
            }
            LoginState.FORGOT_PASSWORD -> {
                goToLogInEmailScreen()
            }
        }
    }

    private fun changePasswordVisibility(isPasswordVisible: Boolean) {
        _logInEmailScreenState.update { it.copy(isPasswordVisible = isPasswordVisible) }
    }

    data class LogInScreenState(
        val loginState: LoginState = LoginState.LOGIN,
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val emailError: TextContainer? = null,
        val passwordError: TextContainer? = null,
    )

    enum class LoginState(val actionButtonText: TextContainer) {
        LOGIN(getTextContainer(R.string.onboarding__login_action_button)),
        FORGOT_PASSWORD(getTextContainer(R.string.onboarding__reset_password_action_button))
    }

    sealed class Event {
        data class Info(val infoMessage: TextContainer) : Event()
        data class Error(val errorInfo: TextContainer) : Event()

        object GoBack : Event()
        object LogInSuccess : Event()
    }
}