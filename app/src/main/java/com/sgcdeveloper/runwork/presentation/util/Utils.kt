package com.sgcdeveloper.runwork.presentation.util

import com.sgcdeveloper.chips.model.ResText
import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.data.model.user.UserGender
import com.sgcdeveloper.runwork.presentation.model.GenderChipModel

val userGenderChips by
lazy {
    listOf(
        GenderChipModel(
            text = ResText(R.string.onboarding__registration_gender_male),
            isEnable = true,
            gender = UserGender.MALE
        ),
        GenderChipModel(
            text = ResText(R.string.onboarding__registration_gender_female),
            isEnable = false,
            gender = UserGender.FEMALE
        ),
        GenderChipModel(
            text = ResText(R.string.onboarding__registration_gender_not_said),
            isEnable = false,
            gender = UserGender.NOT_SAID
        ),
    )
}