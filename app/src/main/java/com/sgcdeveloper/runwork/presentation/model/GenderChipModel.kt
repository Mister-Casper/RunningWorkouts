package com.sgcdeveloper.runwork.presentation.model

import com.sgcdeveloper.chips.model.Text
import com.sgcdeveloper.chips.model.chips.ChipModel
import com.sgcdeveloper.chips.model.chips.TextChipModel
import com.sgcdeveloper.chips.model.toText
import com.sgcdeveloper.runwork.data.model.user.UserGender

class GenderChipModel(
    id: String = "",
    isEnable: Boolean = false,
    text: Text = "".toText(),
    val gender: UserGender
) : TextChipModel(id, isEnable, text) {
    override fun <T : ChipModel> copy(id: String, isEnable: Boolean): T {
        return GenderChipModel(id, isEnable, text, gender) as T
    }
}