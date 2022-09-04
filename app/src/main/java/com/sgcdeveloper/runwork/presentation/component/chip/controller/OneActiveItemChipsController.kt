package com.sgcdeveloper.runwork.presentation.component.chip.controller

import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

class OneActiveItemChipsController<T : ChipModel>(chips: List<T>) : ChipsController<T>(chips) {

    override fun onChipClick(chip: T) {
        deactivateAllChips()
        updateChipActiveState(chip)
    }

    private fun updateChipActiveState(chip: T) {
        updateChips(chips.map { item -> chip.copy(isActive = (item.id == chip.id)) } as List<T>)
    }
}