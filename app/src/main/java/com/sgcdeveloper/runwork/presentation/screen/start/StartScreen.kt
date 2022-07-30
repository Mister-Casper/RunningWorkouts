package com.sgcdeveloper.runwork.presentation.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.sgcdeveloper.runwork.R

@Composable
@Destination(route = "start_screen")
fun StartScreen() {
    Image(
        painter = painterResource(R.drawable.back),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Text("Start")

}