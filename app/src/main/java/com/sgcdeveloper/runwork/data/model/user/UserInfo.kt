package com.sgcdeveloper.runwork.data.model.user

import android.net.Uri

data class UserInfo(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profilePic: Uri = Uri.EMPTY,
    val gender: UserGender = UserGender.NOT_SAID,
)