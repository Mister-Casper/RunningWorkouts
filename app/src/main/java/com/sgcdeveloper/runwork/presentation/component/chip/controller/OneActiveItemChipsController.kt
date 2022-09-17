package com.sgcdeveloper.runwork.presentation.component.chip.controller

import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

class OneActiveItemChipsController(chips: List<ChipModel>) : ChipsController(chips) {

    override fun onChipClick(chip: ChipModel) {
        deactivateAllChips()
        updateChipActiveState(chip)
    }

    fun getActiveChipOrNull(): ChipModel? {
        return chips.find { it.isActive }
    }

    private fun updateChipActiveState(chip: ChipModel) {
        updateChips(chips.map { item -> item.copy(isActive = (item.id == chip.id)) })
    }
}