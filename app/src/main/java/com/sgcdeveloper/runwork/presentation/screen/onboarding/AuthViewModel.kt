package com.sgcdeveloper.runwork.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
import com.sgcdeveloper.runwork.presentation.util.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appRepository: AppRepository
) : ViewModel() {

    private val eventChannel = Channel<Event>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.GoLogInByEmail -> goLogInByEmail()
            is AuthEvent.LogInByGoogle -> tryLogInByGoogle(event.account)
            is AuthEvent.SignOut -> signOut()
            is AuthEvent.LogInByGoogleFailed -> logInByGoogleFailed(event.error)
            AuthEvent.GoRegisterByEmail -> goRegisterByEmail()
        }
    }

    private fun logInByGoogleFailed(error: Throwable) {
        sendEvent(eventChannel, Event.GoogleAuthFailed(error.getAuthErrorInfo()))
    }

    private fun goLogInByEmail() {
        sendEvent(eventChannel, Event.GoLogInByEmail)
    }

    private fun goRegisterByEmail() {
        sendEvent(eventChannel, Event.GoRegistrationByEmail)
    }

    private fun tryLogInByGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                logInByGoogle(it)
            }.addOnFailureListener {
                logInByGoogleFailed(it)
            }
    }

    private fun logInByGoogle(authResult: AuthResult) {
        if (authResult.additionalUserInfo?.isNewUser == false) {
            appRepository.setOnboardingStatus(OnboardingStatus.FINISH)
            sendEvent(eventChannel, Event.GoogleAuthSuccess)
        } else {
            appRepository.setOnboardingStatus(OnboardingStatus.GET_STARTED)
            sendEvent(eventChannel, Event.GoGetStarted)
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
    }

    sealed class Event {
        object GoogleAuthSuccess : Event()
        data class GoogleAuthFailed(val errorInfo: TextContainer) : Event()
        object GoLogInByEmail : Event()
        object GoRegistrationByEmail : Event()
        object GoGetStarted : Event()
    }
}