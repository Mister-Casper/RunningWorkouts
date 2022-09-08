package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import com.google.firebase.auth.FirebaseAuth
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import com.sgcdeveloper.runwork.domain.validators.EmailValidator
import com.sgcdeveloper.runwork.domain.validators.PasswordValidator
import com.sgcdeveloper.runwork.presentation.navigation.NavigableViewModel
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEvent
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEventsHandler
import com.sgcdeveloper.runwork.presentation.screen.destinations.MainScreenDestination
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.getTextContainer
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
import com.sgcdeveloper.runwork.presentation.util.observe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LogInEmailViewModel @Inject constructor(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val firebaseAuth: FirebaseAuth,
    private val appRepository: AppRepository,
    navigationEventsHandler: NavigationEventsHandler,
) : NavigableViewModel(navigationEventsHandler) {

    private val _logInEmailScreenState = MutableStateFlow(LogInScreenState())
    val logInEmailScreenState = _logInEmailScreenState.asStateFlow()

    fun onEvent(logInEvent: LogInEvent) {
        when (logInEvent) {
            is LogInEvent.UpdateEmail -> {
                updateEmail(logInEvent.email)
            }
            is LogInEvent.UpdatePassword -> {
                updatePassword(logInEvent.password)
            }
            is LogInEvent.UpdatePasswordVisibility -> {
                changePasswordVisibility(logInEvent.isVisible)
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
        ).observe(onSuccess = { logIn() }, onFailure = { logInFailed(it) })
    }

    private fun logIn() {
        appRepository.setOnboardingStatus(OnboardingStatus.FINISH)
        sendEvent(NavigationEvent.NavigateTo(MainScreenDestination))
    }

    private fun logInFailed(exception: Exception) {
        sendEvent(NavigationEvent.ShowErrorMassage(exception.getAuthErrorInfo()))
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
            sendEvent(NavigationEvent.ShowInfoMassage(getTextContainer(R.string.onboarding__password_email_sent)))
        }, onFailure = {
            sendEvent(NavigationEvent.ShowErrorMassage(it.getAuthErrorInfo()))
        })
    }

    private fun showEmailError() {
        _logInEmailScreenState.update { it.copy(emailError = getTextContainer(R.string.onboarding__login_email_error)) }
    }

    private fun showPasswordError() {
        _logInEmailScreenState.update { it.copy(passwordError = getTextContainer(R.string.onboarding__login_password_error)) }
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
                sendEvent(NavigationEvent.GoBack)
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
}