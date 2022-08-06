package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.domain.validators.EmailValidator
import com.sgcdeveloper.runwork.domain.validators.PasswordValidator
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.getTextContainer
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
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
    private val passwordValidator: PasswordValidator,
    private val firebaseAuth: FirebaseAuth
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
            LogInEvent.Action -> {
                doAction()
            }
            LogInEvent.ForgotPassword -> {
                goToForgotPasswordScreen()
            }
            LogInEvent.BackPressed -> {
                onBackPressed()
            }
            is LogInEvent.UpdatePasswordVisibility -> {
                changePasswordVisibility(logInEvent.isVisible)
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
        firebaseAuth.createUserWithEmailAndPassword(
            _logInEmailScreenState.value.email,
            _logInEmailScreenState.value.password
        )
            .addOnSuccessListener {
                viewModelScope.launch {
                    _eventChannel.send(Event.LogInSuccess)
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _eventChannel.send(Event.LogInFailed(it.getAuthErrorInfo()))
                }
            }
    }

    private fun doForgotPasswordAction() {
        val isEmailValid = emailValidator.isValid(_logInEmailScreenState.value.email)
        when {
            !isEmailValid -> {
                showEmailError()
            }
            else -> {
                doSendForgotPasswordEmail()
            }
        }
    }

    private fun doSendForgotPasswordEmail() {
        firebaseAuth.sendPasswordResetEmail(
            _logInEmailScreenState.value.email
        )
            .addOnSuccessListener {
                goToLogInEmailScreen()
                viewModelScope.launch {
                    _eventChannel.send(Event.ForgotPasswordSuccess(getTextContainer(R.string.forgot_password_success)))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _eventChannel.send(Event.ForgotPasswordFailed(it.getAuthErrorInfo()))
                }
            }
    }

    private fun showEmailError() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            emailError = getTextContainer(
                R.string.onboarding_login_email_error
            )
        )
    }

    private fun showPasswordError() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            passwordError = getTextContainer(
                R.string.onboarding_login_password_error
            )
        )
    }

    private fun goToForgotPasswordScreen() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            loginState = LoginState.FORGOT_PASSWORD
        )
        viewModelScope.launch {
            _eventChannel.send(Event.GoToForgotPasswordScreen)
        }
    }

    private fun goToLogInEmailScreen() {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            loginState = LoginState.LOGIN,
        )
        viewModelScope.launch {
            _eventChannel.send(Event.GoToLogInScreen)
        }
    }

    private fun onBackPressed() {
        if (_logInEmailScreenState.value.loginState == LoginState.FORGOT_PASSWORD) {
            goToLogInEmailScreen()
        } else {
            viewModelScope.launch {
                _eventChannel.send(Event.GoBack)
            }
        }
    }

    fun changePasswordVisibility(isPasswordVisible: Boolean) {
        _logInEmailScreenState.value = _logInEmailScreenState.value.copy(
            isPasswordVisible = isPasswordVisible
        )
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
        object GoToLogInScreen : Event()
        object GoToForgotPasswordScreen : Event()
        object GoBack : Event()

        object LogInSuccess : Event()
        data class LogInFailed(val errorInfo: TextContainer) : Event()

        data class ForgotPasswordSuccess(val infoMessage: TextContainer) : Event()
        data class ForgotPasswordFailed(val errorInfo: TextContainer) : Event()
    }
}