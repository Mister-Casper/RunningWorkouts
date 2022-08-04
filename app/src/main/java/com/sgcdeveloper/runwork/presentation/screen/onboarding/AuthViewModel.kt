package com.sgcdeveloper.runwork.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    private val eventChannel = Channel<Event>()
    val eventFlow = eventChannel.consumeAsFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.LogInByEmail -> logInByEmail()
            is AuthEvent.LogInByGoogle -> logInByGoogle(event.account)
            is AuthEvent.SignOut -> signOut()
            AuthEvent.LogInByGoogleFailed -> logInByGoogleFailed()
        }
    }

    private fun logInByGoogleFailed() {

    }

    private fun logInByEmail() {
        viewModelScope.launch {
            eventChannel.send(Event.LogInByEmail)
        }
    }

    private fun logInByGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        eventChannel.send(Event.GoogleAuthSuccess)
                    }
                } else {

                }
            }
    }

    private fun signOut() {
        firebaseAuth.signOut()
    }

    sealed class Event {
        object GoogleAuthSuccess : Event()
        object LogInByEmail : Event()
    }
}