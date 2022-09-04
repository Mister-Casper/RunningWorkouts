package com.sgcdeveloper.runwork.presentation.component.chip.model

import com.sgcdeveloper.runwork.data.model.user.UserGender
import com.sgcdeveloper.runwork.presentation.util.TextContainer
import com.sgcdeveloper.runwork.presentation.util.TextContainer.Companion.toTextContainer

open class ChipModel(
    val id: String = "",
    val text: TextContainer = "".toTextContainer(),
    val isActive: Boolean = false
) {

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + isActive.hashCode()
        return result
    }

    fun copy(id: String = this.id, text: TextContainer = this.text, isActive: Boolean = this.isActive) =
        ChipModel(id, text, isActive)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChipModel

        if (id != other.id) return false
        if (text != other.text) return false
        if (isActive != other.isActive) return false

        return true
    }

}

class GenderChipModel(
    id: String = "",
    text: TextContainer = "".toTextContainer(),
    isActive: Boolean = false,
    val userGender: UserGender
) : ChipModel(id, text, isActive)