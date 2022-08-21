package com.sgcdeveloper.runwork.presentation.screen.onboarding.registration

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
fun RegistrationScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel = hiltViewModel()) {
    AuthComponent(
        navigator = navigator,
        header = stringResource(id = R.string.onboarding__registration_header),
        description = stringResource(id = R.string.onboarding__registration_description),
        emailAction = { authViewModel.onEvent(AuthEvent.GoRegisterByEmail) },
        authViewModel = authViewModel
    )
}