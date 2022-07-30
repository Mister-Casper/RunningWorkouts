package com.sgcdeveloper.runwork.presentation.screen.progress

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.sgcdeveloper.runwork.presentation.navigation.Navigation

@Composable
@Destination(navGraph = Navigation.GRAPH_BOTTOM_NAVIGATION)
fun ProgressScreen() {
    Text(text = "Progress Screen")
}