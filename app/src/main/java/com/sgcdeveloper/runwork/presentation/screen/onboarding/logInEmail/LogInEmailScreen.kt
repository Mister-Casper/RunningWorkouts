package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.InputField
import com.sgcdeveloper.runwork.presentation.component.PasswordInputField
import com.sgcdeveloper.runwork.presentation.screen.destinations.MainScreenDestination
import com.sgcdeveloper.runwork.presentation.theme.dark_blue
import com.sgcdeveloper.runwork.presentation.theme.dark_gray
import com.sgcdeveloper.runwork.presentation.theme.white
import com.sgcdeveloper.runwork.presentation.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
fun LogInEmailScreen(navigator: DestinationsNavigator, logInEmailViewModel: LogInEmailViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current

    val screenState = logInEmailViewModel.logInEmailScreenState.collectAsState().value

    val coroutinesScope = rememberCoroutineScope()

    val passwordAlphaAnimation = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launchWhenStarted {
            logInEmailViewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is LogInEmailViewModel.Event.GoToForgotPasswordScreen -> {
                        goToForgotPasswordScreenAnimation(
                            coroutinesScope,
                            passwordAlphaAnimation,
                        )
                    }
                    is LogInEmailViewModel.Event.LogInSuccess -> {
                        navigator.navigate(MainScreenDestination())
                    }
                    LogInEmailViewModel.Event.GoBack -> {
                        navigator.navigateUp()
                    }
                    LogInEmailViewModel.Event.GoToLogInScreen -> {
                        goToLogInScreenAnimation(
                            coroutinesScope,
                            passwordAlphaAnimation,
                        )
                    }
                    is LogInEmailViewModel.Event.ForgotPasswordFailed -> {
                        context.shoErrorToast(event.errorInfo)
                    }
                    is LogInEmailViewModel.Event.ForgotPasswordSuccess -> {
                        context.showSuccessToast(event.infoMessage)
                    }
                    is LogInEmailViewModel.Event.LogInFailed -> {
                        context.shoErrorToast(event.errorInfo)
                    }
                }
            }
        }
    }

    FullscreenImage(gradientColors = listOf(dark_gray, Color.Transparent, Color.Transparent))

    Column(Modifier.padding(start = 6.dp, end = 6.dp, top = 16.dp)) {
        val lastFieldKeyboardAction = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        })

        val emailFieldKeyboardAction = if (screenState.loginState == LogInEmailViewModel.LoginState.FORGOT_PASSWORD) {
            lastFieldKeyboardAction
        } else
            KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }

        InputField(
            label = stringResource(id = R.string.onboarding__email_registration),
            value = screenState.email,
            onValueChange = { newEmail ->
                logInEmailViewModel.onEvent(LogInEvent.UpdateEmail(newEmail))
            },
            errorText = screenState.emailError?.getString(),
            focusManager = focusManager,
            keyboardActions = emailFieldKeyboardAction
        )
        AnimatedVisibility(visible = screenState.loginState == LogInEmailViewModel.LoginState.LOGIN) {
            PasswordInputField(
                label = stringResource(id = R.string.onboarding__password_registration),
                value = screenState.password,
                onValueChange = { newPassword ->
                    logInEmailViewModel.onEvent(LogInEvent.UpdatePassword(newPassword))
                },
                errorText = screenState.passwordError?.getString(),
                focusManager = focusManager,
                passwordVisibility = screenState.isPasswordVisible,
                changePasswordVisibility = {
                    logInEmailViewModel.onEvent(LogInEvent.UpdatePasswordVisibility(it))
                },
                modifier = Modifier.alpha(passwordAlphaAnimation.value),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = lastFieldKeyboardAction
            )
        }
        Column(Modifier.fillMaxWidth()) {
            ActionButtonBlock(screenState.loginState.actionButtonText.getString()) {
                logInEmailViewModel.onEvent(LogInEvent.Action)
            }
            if (screenState.loginState == LogInEmailViewModel.LoginState.LOGIN) {
                ForgotPasswordBlock {
                    logInEmailViewModel.onEvent(LogInEvent.ForgotPassword)
                }
            }
        }
    }

    BackHandler {
        logInEmailViewModel.onEvent(LogInEvent.BackPressed)
    }
}

@Composable
private fun ActionButtonBlock(actionTest: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = dark_blue.copy(alpha = 0.8f),
            contentColor = white
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Text(
            text = actionTest,
            modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun ForgotPasswordBlock(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp, top = 12.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = stringResource(id = R.string.onboarding__forgot_password),
            modifier = Modifier.padding(top = 6.dp, end = 6.dp, start = 6.dp, bottom = 6.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = white
        )
    }
}

private fun goToForgotPasswordScreenAnimation(
    coroutinesScope: CoroutineScope,
    passwordAlphaAnimation: Animatable<Float, AnimationVector1D>,
) {
    coroutinesScope.launch {
        passwordAlphaAnimation.animateTo(0f, animationSpec = tween(LONG_ANIMATION_TIME))
    }
}

private fun goToLogInScreenAnimation(
    coroutinesScope: CoroutineScope,
    passwordVisibilityAnimation: Animatable<Float, AnimationVector1D>,
) {
    coroutinesScope.launch {
        passwordVisibilityAnimation.animateTo(VISIBLE, animationSpec = tween(LONG_ANIMATION_TIME))
    }
}