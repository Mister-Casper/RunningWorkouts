package com.sgcdeveloper.runwork.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sgcdeveloper.runwork.presentation.util.sendEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

open class NavigableViewModel @Inject constructor(
    private val navigationEventsHandler: NavigationEventsHandler
) : ViewModel() {

    private var _destinationsNavigator: DestinationsNavigator? = null

    private val _navigationEventsChannel = Channel<NavigationEvent>(Channel.BUFFERED)

    init {
        viewModelScope.launch {
            _navigationEventsChannel.receiveAsFlow()
                .collectLatest { event ->
                    navigationEventsHandler.handle(
                        navigationEvent = event,
                        navigator = _destinationsNavigator
                            ?: throw Exception("You should use navigableHiltViewModel() to provide your view model")
                    )
                }
        }
    }

    fun setNavigator(destinationsNavigator: DestinationsNavigator) {
        _destinationsNavigator = destinationsNavigator
    }

    fun sendEvent(event: NavigationEvent) {
        sendEvent(_navigationEventsChannel, event)
    }
}