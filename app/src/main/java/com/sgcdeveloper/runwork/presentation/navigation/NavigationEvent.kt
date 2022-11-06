package com.sgcdeveloper.runwork.presentation.navigation

import android.content.Context
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.presentation.screen.destinations.DirectionDestination
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.shoErrorToast
import com.sgcdeveloper.runwork.presentation.util.showSuccessToast

abstract class NavigationEvent {
    abstract fun execute(context: Context, navigator: DestinationsNavigator)

    class ShowErrorMassage(private val messageText: TextContainer) : NavigationEvent() {
        override fun execute(context: Context, navigator: DestinationsNavigator) {
            context.shoErrorToast(messageText)
        }
    }

    class ShowInfoMassage(private val messageText: TextContainer) : NavigationEvent() {
        override fun execute(context: Context, navigator: DestinationsNavigator) {
            context.showSuccessToast(messageText)
        }
    }

    class NavigateTo(private val navigationDestination: DirectionDestination) : NavigationEvent() {
        override fun execute(context: Context, navigator: DestinationsNavigator) {
            navigator.navigate(navigationDestination)
        }
    }

    object GoBack : NavigationEvent() {
        override fun execute(context: Context, navigator: DestinationsNavigator) {
            navigator.navigateUp()
        }
    }
}