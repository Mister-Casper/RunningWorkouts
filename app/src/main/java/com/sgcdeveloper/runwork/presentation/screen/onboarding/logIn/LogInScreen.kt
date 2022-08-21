package com.sgcdeveloper.runwork.presentation.screen.onboarding.logIn

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.auth.AuthComponent
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthEvent
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthViewModel

@Composable
@Destination
fun LogInScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel = hiltViewModel()) {
    AuthComponent(
        navigator = navigator,
        header = stringResource(id = R.string.onboarding__login_header),
        description = stringResource(id = R.string.onboarding__login_text),
        emailAction = { authViewModel.onEvent(AuthEvent.GoLogInByEmail) },
        authViewModel = authViewModel
    )
}