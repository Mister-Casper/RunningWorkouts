package com.sgcdeveloper.runwork.presentation.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.Task
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.presentation.navigation.NavigableViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

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

@Composable
inline fun <reified VM : NavigableViewModel> navigableHiltViewModel(
    destinationsNavigator: DestinationsNavigator,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
): VM {
    val factory = createHiltViewModelFactory(viewModelStoreOwner)
    val viewModel = viewModel(viewModelStoreOwner, factory = factory) as VM
    viewModel.setNavigator(destinationsNavigator)
    return viewModel(viewModelStoreOwner, factory = factory)
}

@Composable
@PublishedApi
internal fun createHiltViewModelFactory(
    viewModelStoreOwner: ViewModelStoreOwner
): ViewModelProvider.Factory? = if (viewModelStoreOwner is NavBackStackEntry) {
    HiltViewModelFactory(
        context = LocalContext.current,
        navBackStackEntry = viewModelStoreOwner
    )
} else {
    // Use the default factory provided by the ViewModelStoreOwner
    // and assume it is an @AndroidEntryPoint annotated fragment or activity
    null
}