package com.sgcdeveloper.runwork.presentation.component.chip.controller

import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

abstract class ChipsController(chips: List<ChipModel>) {

    abstract fun onChipClick(chip: ChipModel)

    protected val chips: MutableList<ChipModel> = chips.toMutableList()

    fun getAllChips(): List<ChipModel> = chips.toList()

    fun deactivateAllChips() {
        val clearedChips = chips.map { it.copy(isActive = false) }
        updateChips(clearedChips)
    }

    protected fun updateChips(chips: List<ChipModel>) {
        this.chips.clear()
        this.chips.addAll(chips)
    }
}