package com.sgcdeveloper.runwork.presentation.screen.start

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.component.FullscreenImage
import com.sgcdeveloper.runwork.presentation.screen.destinations.LogInScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.RegistrationScreenDestination
import com.sgcdeveloper.runwork.presentation.theme.dark_blue
import com.sgcdeveloper.runwork.presentation.theme.dark_gray
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
@Destination(route = "start_screen")
fun StartScreen(navigator: DestinationsNavigator) {
    FullscreenImage()
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
                    .padding(top = 12.dp, bottom = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.start_journey),
                    modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Button(
                onClick = { navigator.navigate(LogInScreenDestination) },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = dark_gray.copy(alpha = 0.5f),
                    contentColor = white
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.already_have_account),
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}