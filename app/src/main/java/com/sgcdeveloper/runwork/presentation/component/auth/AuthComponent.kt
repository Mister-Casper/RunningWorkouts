package com.sgcdeveloper.runwork.presentation.component.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.presentation.component.BackButton
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.LogInEmailScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.RegistrationEmailScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthEvent
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthViewModel
import com.sgcdeveloper.runwork.presentation.theme.white
import com.sgcdeveloper.runwork.presentation.util.shoErrorToast
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthComponent(
    navigator: DestinationsNavigator,
    header: String,
    description: String,
    emailAction: () -> Unit,
    authViewModel: AuthViewModel
) {
    AuthComponentContent(navigator, authViewModel, header, description, emailAction)
}


@Composable
fun AuthComponentContent(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel,
    header: String,
    description: String,
    emailAction: () -> Unit
) {
    FullscreenImage()
    Column(Modifier.fillMaxSize()) {
        BackButton(navigator = navigator)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = header,
                color = white,
                modifier = Modifier
                    .padding(top = 4.dp, start = 32.dp, bottom = 4.dp, end = 16.dp),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = description,
                color = white,
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, bottom = 24.dp, end = 16.dp),
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        RegistrationComponent(
            onGoogleSignInSuccessful = { account -> authViewModel.onEvent(AuthEvent.LogInByGoogle(account)) },
            onGoogleSignInFailed = { error -> authViewModel.onEvent(AuthEvent.LogInByGoogleFailed(error)) },
            actionEmail = { emailAction() })
    }
}