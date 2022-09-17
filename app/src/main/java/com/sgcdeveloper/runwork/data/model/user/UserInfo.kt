package com.sgcdeveloper.runwork.data.model.user

data class UserInfo(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profilePic: String = "",
    val gender: UserGender = UserGender.NOT_SAID,
)