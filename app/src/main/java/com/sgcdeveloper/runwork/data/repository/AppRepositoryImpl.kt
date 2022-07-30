package com.sgcdeveloper.runwork.data.repository

import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.domain.repository.AppRepository

class AppRepositoryImpl(private val sharedPreferences: SharedPreferences) : AppRepository {
    override suspend fun getOnboardingStatus(): OnboardingStatus {
        return OnboardingStatus.valueOf(sharedPreferences.getValue(ONBOARDING_STATUS, OnboardingStatus.START.name))
    }

    override suspend fun setOnboardingStatus(status: OnboardingStatus) {
        sharedPreferences.putValue(ONBOARDING_STATUS, status.name)
    }

    companion object {
        const val ONBOARDING_STATUS = "onboarding_status"
    }
}