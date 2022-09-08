package com.sgcdeveloper.runwork.presentation.navigation

import android.content.Context
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import javax.inject.Inject

class NavigationEventsHandler @Inject constructor(private val context: Context) {

    fun handle(navigationEvent: NavigationEvent, navigator: DestinationsNavigator) {
        navigationEvent.execute(context, navigator)
    }

}