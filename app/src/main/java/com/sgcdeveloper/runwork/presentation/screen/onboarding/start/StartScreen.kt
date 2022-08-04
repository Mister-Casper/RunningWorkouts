package com.sgcdeveloper.runwork.presentation.screen.onboarding.start

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.screen.destinations.LogInScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.RegistrationScreenDestination
import com.sgcdeveloper.runwork.presentation.theme.black
import com.sgcdeveloper.runwork.presentation.theme.dark_blue
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
@Destination
fun StartScreen(navigator: DestinationsNavigator) {
    FullscreenImage(
        gradientColors = listOf(
            Color.Transparent,
            Color.Transparent,
            black,
        )
    )
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(end = 18.dp, start = 18.dp, bottom = 32.dp)
        ) {
            Button(
                onClick = { navigator.navigate(RegistrationScreenDestination) },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = dark_blue.copy(alpha = 0.8f),
                    contentColor = white
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.onboarding__registration_header),
                    modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Box(modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navigator.navigate(LogInScreenDestination)
                }) {
                Text(
                    text = stringResource(R.string.onboarding__login),
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .align(Alignment.Center),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    BackHandler {}
}