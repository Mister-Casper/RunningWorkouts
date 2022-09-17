package com.sgcdeveloper.runwork.data.repository

import com.sgcdeveloper.runwork.data.model.core.OnboardingStatus
import com.sgcdeveloper.runwork.data.model.user.UserInfo
import com.sgcdeveloper.runwork.domain.repository.AppRepository

class AppRepositoryImpl(private val sharedPreferencesStore: SharedPreferencesStore) : AppRepository {
    override fun getOnboardingStatus(): OnboardingStatus {
        return OnboardingStatus.valueOf(sharedPreferencesStore.getValue(ONBOARDING_STATUS, OnboardingStatus.START.name))
    }

    override fun setOnboardingStatus(status: OnboardingStatus) {
        sharedPreferencesStore.putValue(ONBOARDING_STATUS, status.name)
    }

    override fun getUserInfo(): UserInfo {
        return sharedPreferencesStore.getObject(USER_INFO, UserInfo(), UserInfo::class.java)
    }

    override fun setUserInfo(userInfo: UserInfo) {
        sharedPreferencesStore.putValue(USER_INFO, userInfo)
    }

    companion object {
        const val ONBOARDING_STATUS = "onboarding_status"
        const val USER_INFO = "user_info"
    }
}