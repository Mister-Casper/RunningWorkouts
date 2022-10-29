package com.sgcdeveloper.runwork.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.presentation.theme.dark_gray

@Composable
fun FullscreenImage(
    @DrawableRes image: Int = R.drawable.back,
    gradientColors: List<Color> = listOf(
        dark_gray,
        Color.Transparent,
        Color.Transparent,
    )
) {
    Image(
        painter = painterResource(image),
        contentDescription = "",
        modifier = Modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = gradientColors
                        ),
                        blendMode = BlendMode.SrcAtop
                    )
                }
            }
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}