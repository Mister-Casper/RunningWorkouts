package com.sgcdeveloper.runwork.domain.repository

import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus

interface AppRepository {

    suspend fun getOnboardingStatus(): OnboardingStatus

    suspend fun setOnboardingStatus(status: OnboardingStatus)

}