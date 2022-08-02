package com.sgcdeveloper.runwork.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.sgcdeveloper.runwork.R

@Composable
fun FullscreenImage(@DrawableRes image: Int = R.drawable.back) {
    Image(
        painter = painterResource(image),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}