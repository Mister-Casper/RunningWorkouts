package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.ActionButtonBlock
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.InputField
import com.sgcdeveloper.runwork.presentation.component.PasswordInputField
import com.sgcdeveloper.runwork.presentation.theme.dark_gray
import com.sgcdeveloper.runwork.presentation.theme.white
import com.sgcdeveloper.runwork.presentation.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
fun LogInEmailScreen(
    navigator: DestinationsNavigator,
    logInEmailViewModel: LogInEmailViewModel = navigableHiltViewModel()
) {
    logInEmailViewModel.setNavigator(navigator)

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val screenState = logInEmailViewModel.logInEmailScreenState.collectAsState().value

    val passwordAlphaAnimation = remember { Animatable(1f) }

    FullscreenImage(gradientColors = listOf(dark_gray, dark_gray.copy(0.7f), Color.Transparent, Color.Transparent))

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
            keyboardActions = emailFieldKeyboardAction,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                modifier = Modifier
                    .alpha(passwordAlphaAnimation.value)
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = lastFieldKeyboardAction
            )
        }
        Column(Modifier.fillMaxWidth()) {
            ActionButtonBlock(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 16.dp),
                actionTest = screenState.loginState.actionButtonText.getString()
            ) {
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
private fun ForgotPasswordBlock(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp, top = 16.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = stringResource(id = R.string.onboarding__forgot_password),
            modifier = Modifier
                .padding(top = 6.dp, end = 6.dp, start = 6.dp, bottom = 6.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = white
        )
    }
}