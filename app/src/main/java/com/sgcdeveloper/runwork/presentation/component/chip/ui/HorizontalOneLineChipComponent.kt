package com.sgcdeveloper.runwork.presentation.component.chip.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

@Composable
fun HorizontalOneLineChipComponent(
    chips: List<ChipModel>,
    defaultColor: Color,
    activeChipColor: Color,
    onClick: (chipModel: ChipModel) -> Unit
) {
    LazyRow(Modifier.fillMaxWidth()) {
        items(chips, key = { it.id }) { chip ->
            Chip(
                chip = chip,
                defaultColor = defaultColor,
                activeChipColor = if (chip.isActive) activeChipColor else defaultColor,
                onClick = { onClick(chip) }
            )
        }
    }
}

@Composable
fun Chip(chip: ChipModel, defaultColor: Color, activeChipColor: Color, onClick: (chipModel: ChipModel) -> Unit) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .background(
                color = (if (chip.isActive) activeChipColor else defaultColor).copy(alpha = 0.2f),
                shape = RoundedCornerShape(36.dp)
            )
            .border(
                border = BorderStroke(width = 1.dp, color = if (chip.isActive) activeChipColor else defaultColor),
                shape = RoundedCornerShape(36.dp)
            )
    ) {
        Text(
            text = chip.text.getString(),
            color = if (chip.isActive) activeChipColor else defaultColor,
            modifier = Modifier
                .clickable(onClick = { onClick(chip) })
                .padding(start = 18.dp, end = 18.dp, top = 12.dp, bottom = 12.dp),
        )
    }
}