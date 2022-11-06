package com.sgcdeveloper.runwork.presentation.screen.onboarding

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import com.sgcdeveloper.runwork.presentation.navigation.NavigableViewModel
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEvent
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEventsHandler
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.LogInEmailScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.MainScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.RegistrationEmailScreenDestination
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appRepository: AppRepository,
    navigationEventsHandler: NavigationEventsHandler
) : NavigableViewModel(navigationEventsHandler) {

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
        sendEvent(NavigationEvent.ShowErrorMassage(error.getAuthErrorInfo()))
    }

    private fun goLogInByEmail() {
        sendEvent(NavigationEvent.NavigateTo(LogInEmailScreenDestination))
    }

    private fun goRegisterByEmail() {
        sendEvent(NavigationEvent.NavigateTo(RegistrationEmailScreenDestination))
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
            sendEvent(NavigationEvent.NavigateTo(MainScreenDestination))
        } else {
            appRepository.setOnboardingStatus(OnboardingStatus.GET_STARTED)
            sendEvent(NavigationEvent.NavigateTo(GetStartedScreenDestination))
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
    }
}