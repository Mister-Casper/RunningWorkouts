package com.sgcdeveloper.runwork.domain.repository

import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.data.model.user.UserInfo

interface AppRepository {

    fun getOnboardingStatus(): OnboardingStatus

    fun setOnboardingStatus(status: OnboardingStatus)

    fun getUserInfo(): UserInfo

    fun setUserInfo(userInfo: UserInfo)

}