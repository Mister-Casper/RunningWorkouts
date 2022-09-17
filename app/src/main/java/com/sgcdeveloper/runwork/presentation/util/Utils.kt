package com.sgcdeveloper.runwork.presentation.util

import com.sgcdeveloper.runwork.R
import com.sgcdeveloper.runwork.data.model.user.UserGender
import com.sgcdeveloper.runwork.presentation.component.chip.model.ChipModel

val userGenderChips by
lazy {
    listOf(
        ChipModel(
            "1",
            TextContainer.ResContainer(R.string.onboarding__registration_gender_male),
            true,
            UserGender.MALE
        ),
        ChipModel(
            "2",
            TextContainer.ResContainer(R.string.onboarding__registration_gender_female),
            false,
            UserGender.FEMALE
        ),
        ChipModel(
            "3",
            TextContainer.ResContainer(R.string.onboarding__registration_gender_not_said),
            false,
            UserGender.NOT_SAID
        ),
    )
}