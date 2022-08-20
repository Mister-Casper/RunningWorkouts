package com.sgcdeveloper.runwork.presentation.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

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

fun <T> ViewModel.sendEvent(channel: Channel<T>, event: T) {
    viewModelScope.launch {
        channel.send(event)
    }
}

fun <T> Task<T>.observe(onSuccess: (result: T) -> Unit, onFailure: (exception: Exception) -> Unit) {
    addOnSuccessListener {
        onSuccess(it)
    }
    addOnFailureListener {
        onFailure(it)
    }
}