package com.sgcdeveloper.runwork.presentation.screen.onboarding.registrationEmail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.InputField
import com.sgcdeveloper.runwork.presentation.component.PasswordInputField
import com.sgcdeveloper.runwork.presentation.component.chip.ui.HorizontalOneLineChipComponent
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.theme.dark_blue
import com.sgcdeveloper.runwork.presentation.theme.dark_gray
import com.sgcdeveloper.runwork.presentation.theme.white
import com.sgcdeveloper.runwork.presentation.util.shoErrorToast
import com.sgcdeveloper.runwork.presentation.util.showSuccessToast

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
fun RegistrationEmailScreen(
    navigator: DestinationsNavigator,
    registrationEmailViewModel: RegistrationEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current

    val screenState = registrationEmailViewModel.registrationEmailScreenState.collectAsState().value

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            registrationEmailViewModel.eventFlow.collect { event ->
                when (event) {
                    is RegistrationEmailViewModel.Event.Error -> {
                        context.shoErrorToast(event.errorInfo)
                    }
                    is RegistrationEmailViewModel.Event.Info -> {
                        context.showSuccessToast(event.infoMessage)
                    }
                    RegistrationEmailViewModel.Event.RegistrationSuccess -> {
                        navigator.navigate(GetStartedScreenDestination)
                    }
                }
            }
        }
    }

    FullscreenImage(gradientColors = listOf(dark_gray.copy(0.5f), dark_gray.copy(0.5f)))

    Column(Modifier.padding(start = 6.dp, end = 6.dp, top = 16.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(CircleShape)
                    .background(dark_gray)
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_photo),
                    contentDescription = "user icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            Column(Modifier.weight(2f)) {
                InputField(
                    label = stringResource(id = R.string.onboarding__first_name_registration),
                    value = screenState.firstName,
                    onValueChange = { newFirstName ->
                        registrationEmailViewModel.onEvent(RegistrationEvent.UpdateFirstName(newFirstName))
                    },
                    errorText = screenState.emailError?.getString(),
                    focusManager = focusManager,
                )
                InputField(
                    label = stringResource(id = R.string.onboarding__second_name_registration),
                    value = screenState.lastName,
                    onValueChange = { newLastName ->
                        registrationEmailViewModel.onEvent(RegistrationEvent.UpdateLastName(newLastName))
                    },
                    errorText = screenState.passwordError?.getString(),
                    focusManager = focusManager,
                )
            }
        }
        HorizontalOneLineChipComponent(
            chips = screenState.genderChips,
            defaultColor = white,
            activeChipColor = dark_blue
        ) { registrationEmailViewModel.onEvent(RegistrationEvent.UpdateGender(it)) }
        InputField(
            label = stringResource(id = R.string.onboarding__email_registration),
            value = screenState.email,
            onValueChange = { newEmail ->
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdateEmail(newEmail))
            },
            errorText = screenState.emailError?.getString(),
            focusManager = focusManager,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        PasswordInputField(
            label = stringResource(id = R.string.onboarding__password_registration),
            value = screenState.password,
            onValueChange = { newPassword ->
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdatePassword(newPassword))
            },
            errorText = screenState.passwordError?.getString(),
            focusManager = focusManager,
            passwordVisibility = screenState.isPasswordVisible,
            changePasswordVisibility = {
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdatePasswordVisibility(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        PasswordInputField(
            label = stringResource(id = R.string.onboarding__confirm_password_registration),
            value = screenState.confirmPassword,
            onValueChange = { newPassword ->
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdateConfirmPassword(newPassword))
            },
            errorText = screenState.passwordError?.getString(),
            focusManager = focusManager,
            passwordVisibility = screenState.isPasswordVisible,
            changePasswordVisibility = {
                registrationEmailViewModel.onEvent(RegistrationEvent.UpdatePasswordVisibility(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
    }
}