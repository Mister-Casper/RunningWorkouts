package com.sgcdeveloper.runwork.presentation.component.chip.controller

import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

abstract class ChipsController<T : ChipModel>(chips: List<T>) {

    abstract fun onChipClick(chip: T)

    protected val chips:MutableList<T> = chips.toMutableList()

    fun getAllChips(): List<T> = chips

    fun deactivateAllChips() {
        val clearedChips = chips.map { it.copy(isActive = false) } as List<T>
        chips.clear()
        chips.addAll(clearedChips)
    }

    protected fun updateChips(chips: List<T>) {
        this.chips.clear()
        this.chips.addAll(chips)
    }
}