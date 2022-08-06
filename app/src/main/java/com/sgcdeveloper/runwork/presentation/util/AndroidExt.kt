package com.sgcdeveloper.runwork.presentation.util

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.dmoral.toasty.Toasty

fun Context.shoErrorToast(text: TextContainer) {
    Toasty.error(
        this,
        text.getString(this),
        Toasty.LENGTH_LONG,
        true
    ).show()
}

fun Context.showSuccessToast(text: TextContainer) {
    Toasty.success(
        this,
        text.getString(this),
        Toasty.LENGTH_LONG,
        true
    ).show()
}