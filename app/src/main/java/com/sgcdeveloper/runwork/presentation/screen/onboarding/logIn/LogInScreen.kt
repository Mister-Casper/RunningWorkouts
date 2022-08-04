package com.sgcdeveloper.runwork.presentation.screen.onboarding.logIn

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.BackButton
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.registration.RegistrationComponent
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthEvent
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthViewModel
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
@Destination("/logIn")
fun LogInScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel = hiltViewModel()) {

    when (authViewModel.eventFlow.collectAsState(initial = null).value) {
        is AuthViewModel.Event.GoogleAuthSuccess -> {
            navigator.navigate(GetStartedScreenDestination)
        }
        AuthViewModel.Event.LogInByEmail -> {
            navigator.navigate(GetStartedScreenDestination)
        }
        else -> Unit
    }

    FullscreenImage()
    Column(Modifier.fillMaxSize()) {
        BackButton(navigator = navigator)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.welcome_back),
                color = white,
                modifier = Modifier
                    .padding(top = 4.dp, start = 32.dp, bottom = 4.dp, end = 16.dp),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = stringResource(id = R.string.log_in_to_continue),
                color = white,
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, bottom = 24.dp, end = 16.dp),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        RegistrationComponent(
            onGoogleSignInSuccessful = { account -> authViewModel.onEvent(AuthEvent.LogInByGoogle(account)) },
            onGoogleSignInFailed = { authViewModel.onEvent(AuthEvent.LogInByGoogleFailed) },
            actionEmail = { authViewModel.onEvent(AuthEvent.LogInByEmail) })
    }
}