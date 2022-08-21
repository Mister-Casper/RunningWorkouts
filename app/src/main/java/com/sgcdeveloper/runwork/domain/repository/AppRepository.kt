package com.sgcdeveloper.runwork.domain.repository

import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus

interface AppRepository {

    fun getOnboardingStatus(): OnboardingStatus

    fun setOnboardingStatus(status: OnboardingStatus)

}