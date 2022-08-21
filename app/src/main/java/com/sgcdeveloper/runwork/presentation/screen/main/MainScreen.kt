package com.sgcdeveloper.runwork.presentation.screen.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo
import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.presentation.component.BottomBar
import com.sgcdeveloper.runwork.presentation.screen.NavGraphs
import com.sgcdeveloper.runwork.presentation.screen.destinations.GetStartedScreenDestination
import com.sgcdeveloper.runwork.presentation.screen.destinations.StartScreenDestination

@Composable
@Destination(start = true)
fun MainScreen(navigator: DestinationsNavigator, startViewModel: StartViewModel = hiltViewModel()) {

    when (startViewModel.onboardingStatus) {
        OnboardingStatus.START -> {
            navigator.navigate(StartScreenDestination)
        }
        OnboardingStatus.GET_STARTED -> {
            navigator.navigate(GetStartedScreenDestination)
        }
        OnboardingStatus.FINISH -> {
            MainScreenContent()
        }
    }
}

@Composable
fun MainScreenContent() {
    val controller = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(
                currentDestination = controller.currentBackStackEntryAsState().value?.destination?.route,
                onDestinationChange = {
                    controller.navigateTo(it) {
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {
        DestinationsNavHost(
            navController = controller,
            navGraph = NavGraphs.bottomNavigation
        )
    }
}