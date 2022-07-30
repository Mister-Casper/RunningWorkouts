package com.sgcdeveloper.runwork.presentation.screen.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.sgcdeveloper.runwork.presentation.navigation.Navigation

@Composable
@Destination(navGraph = Navigation.GRAPH_BOTTOM_NAVIGATION)
fun ProfileScreen() {
Text(text = "Profile")
}