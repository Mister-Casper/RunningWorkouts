package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.component.InputField
import com.sgcdeveloper.runwork.presentation.theme.dark_blue
import com.sgcdeveloper.runwork.presentation.theme.dark_gray
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
@Destination
fun LogInEmailScreen(navigator: DestinationsNavigator, logInEmailViewModel: LogInEmailViewModel = hiltViewModel()) {
    val screenState = logInEmailViewModel.logInEmailScreenState.collectAsState().value

    FullscreenImage(gradientColors = listOf(dark_gray, Color.Transparent, Color.Transparent))

    Column(Modifier.padding(start = 6.dp, end = 6.dp, top = 16.dp)) {
        InputField(
            label = stringResource(id = R.string.onboarding__email_registration),
            value = screenState.email,
            onValueChange = { newEmail ->
                logInEmailViewModel.onEvent(LogInEvent.UpdateEmail(newEmail))
            },
            errorText = screenState.emailError?.getString(),
        )
        InputField(
            label = stringResource(id = R.string.onboarding__password_registration),
            value = screenState.password,
            onValueChange = { newPassword ->
                logInEmailViewModel.onEvent(LogInEvent.UpdatePassword(newPassword))
            },
            errorText = screenState.passwordError?.getString(),
        )
        Button(
            onClick = {
                logInEmailViewModel.onEvent(LogInEvent.LogIn)
            },
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
                text = stringResource(R.string.onboarding__login_button),
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 12.dp)
                .clickable {
                    logInEmailViewModel.onEvent(LogInEvent.ForgotPassword)
                }
        ) {
            Text(
                text = stringResource(id = R.string.onboarding__forgot_password),
                modifier = Modifier.padding(top = 6.dp, end = 6.dp, start = 6.dp, bottom = 6.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}