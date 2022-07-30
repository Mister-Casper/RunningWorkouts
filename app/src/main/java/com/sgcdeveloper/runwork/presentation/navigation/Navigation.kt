package com.sgcdeveloper.runwork.presentation.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.sgcdeveloper.runwork.presentation.screen.NavGraphs

@Composable
fun Navigation() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}

object Navigation {
    const val GRAPH_BOTTOM_NAVIGATION = "bottomNavigation"
}