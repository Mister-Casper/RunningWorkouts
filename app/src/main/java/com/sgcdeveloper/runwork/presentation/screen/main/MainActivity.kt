package com.sgcdeveloper.runwork.presentation.screen.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.Auth
import com.sgcdeveloper.runwork.presentation.navigation.Navigation
import com.sgcdeveloper.runwork.presentation.screen.onboarding.AuthViewModel
import com.sgcdeveloper.runwork.presentation.theme.RunningWorkoutsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RunningWorkoutsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
            val authViewModel: AuthViewModel = hiltViewModel()
            lifecycleScope.launchWhenCreated {
                authViewModel.eventFlow.collect {
                    Log.e("QWE", "dfdfs")
                }
            }
        }
    }


}