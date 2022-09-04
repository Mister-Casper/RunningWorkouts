package com.sgcdeveloper.runwork.presentation.component.chip.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

@Composable
fun <T : ChipModel> HorizontalOneLineChipComponent(
    chips: List<T>,
    defaultColor: Color,
    activeChipColor: Color,
    onClick: (chipModel: T) -> Unit
) {
    LazyRow(
        Modifier
            .fillMaxWidth()
            .fillMaxWidth()
    ) {
        items(chips, key = { it.id }) { chip ->
            Chip(
                text = chip.text.getString(),
                defaultColor = defaultColor,
                activeChipColor = if (chip.isActive) activeChipColor else defaultColor,
                onClick = { onClick(chip) }
            )
        }
    }
}


@Composable
fun Chip(text: String, defaultColor: Color, activeChipColor: Color, onClick: (chipModel: ChipModel) -> Unit) {

}