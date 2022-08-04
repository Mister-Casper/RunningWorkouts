package com.sgcdeveloper.runwork.presentation.screen.onboarding.logIn

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.BackButton
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.registration.RegistrationComponent
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.LogInEmailScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthEvent
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthViewModel
import com.sgcdeveloper.runwork.presentation.theme.white
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.collectLatest

@Composable
@Destination
fun LogInScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launchWhenStarted {
            authViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AuthViewModel.Event.GoogleAuthSuccess -> {
                        navigator.navigate(GetStartedScreenDestination)
                    }
                    AuthViewModel.Event.LogInByEmail -> {
                        navigator.navigate(LogInEmailScreenDestination)
                    }
                    AuthViewModel.Event.GoogleAuthFailed -> {
                        Toasty.error(
                            context,
                            context.getString(R.string.onboarding__google_auth_error),
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }
                }
            }
        }
    }
    LogInScreenContent(navigator, authViewModel)
}

@Composable
fun LogInScreenContent(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {
    FullscreenImage()
    Column(Modifier.fillMaxSize()) {
        BackButton(navigator = navigator)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.onboarding__login_header),
                color = white,
                modifier = Modifier
                    .padding(top = 4.dp, start = 32.dp, bottom = 4.dp, end = 16.dp),
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = stringResource(id = R.string.onboarding__login_text),
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
            actionEmail = { authViewModel.onEvent(AuthEvent.GoLogInByEmail) })
    }
}