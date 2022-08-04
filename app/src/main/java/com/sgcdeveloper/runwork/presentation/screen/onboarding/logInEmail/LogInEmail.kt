package com.sgcdeveloper.runwork.presentation.screen.onboarding.logInEmail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.presentation.component.BackButton

@Composable
@Destination
fun LogInEmailScreen(navigator: DestinationsNavigator) {
    Column(Modifier.fillMaxSize()) {
        BackButton(navigator = navigator)
        Text(text = "LogInEmailScreen")
    }

}