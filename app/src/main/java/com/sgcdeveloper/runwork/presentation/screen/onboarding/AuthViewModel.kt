package com.sgcdeveloper.runwork.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.getAuthErrorInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    private val eventChannel = Channel<Event>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.GoLogInByEmail -> goLogInByEmail()
            is AuthEvent.LogInByGoogle -> logInByGoogle(event.account)
            is AuthEvent.SignOut -> signOut()
            is AuthEvent.LogInByGoogleFailed -> logInByGoogleFailed(event.error)
        }
    }

    private fun logInByGoogleFailed(error: Throwable) {
        viewModelScope.launch {
            eventChannel.send(Event.GoogleAuthFailed(error.getAuthErrorInfo()))
        }
    }

    private fun goLogInByEmail() {
        viewModelScope.launch {
            eventChannel.send(Event.GoLogInByEmail)
        }
    }

    private fun logInByGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                viewModelScope.launch {
                    viewModelScope.launch {
                        eventChannel.send(Event.GoogleAuthSuccess)
                    }
                }
            }.addOnFailureListener {
                logInByGoogleFailed(it)
            }
    }

    private fun signOut() {
        firebaseAuth.signOut()
    }

    sealed class Event {
        object GoogleAuthSuccess : Event()
        data class GoogleAuthFailed(val errorInfo: TextContainer) : Event()
        object GoLogInByEmail : Event()
    }
}