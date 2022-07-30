package com.sgcdeveloper.runwork.presentation.screen.main

import androidx.lifecycle.ViewModel
import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    val onboardngStatus = flow<OnboardingStatus>
    {
        val status = appRepository.getOnboardingStatus()
        emit(status)
    }


}