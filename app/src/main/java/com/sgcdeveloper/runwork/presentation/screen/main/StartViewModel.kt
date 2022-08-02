package com.sgcdeveloper.runwork.presentation.screen.main

import androidx.lifecycle.ViewModel
import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _onboardingStatus = MutableStateFlow(OnboardingStatus.START)
    val onboardingStatus = _onboardingStatus.asStateFlow()

    init {

    }

}