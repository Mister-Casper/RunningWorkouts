package com.sgcdeveloper.runwork.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.presentation.theme.white

@Composable
fun BackButton(navigator: DestinationsNavigator) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent.copy(alpha = 0.4f))
    ) {
        IconButton(onClick = { navigator.navigateUp() }, modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "", tint = white, modifier = Modifier.size(32.dp))
        }
    }
}