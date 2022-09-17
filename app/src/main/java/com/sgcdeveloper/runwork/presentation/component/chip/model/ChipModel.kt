package com.sgcdeveloper.runwork.presentation.component.chip.model

import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.toTextContainer

data class ChipModel(
    val id: String = "",
    val text: TextContainer = "".toTextContainer(),
    val isActive: Boolean = false,
    val data: Any? = null
)