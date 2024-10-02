package com.antartica.koltin_playground.src.presentation.screens

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.antartica.koltin_playground.src.data.repository.BiometricManager
import com.antartica.koltin_playground.src.data.repository.BiometricManager.BiometricResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricAuthScreen() {
//    val biometricData by viewModel.bioMetricData.collectAsState()
    val context = LocalContext.current as AppCompatActivity
    val promptManager by lazy { BiometricManager(context) }
    val biometricResult by promptManager.promptResults.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Biometric Authentication") })
        },

        ) { paddingValues ->


        val enrollLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                println("Activity result :: $it")
            },
        )

        LaunchedEffect(key1 = biometricResult) {
            if (biometricResult is BiometricResult.AuthenticationNotSet) {
                if (Build.VERSION.SDK_INT >= 30) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL,
                        )
                    }

                    enrollLauncher.launch(enrollIntent)
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {

                promptManager.showBiometricPrompt(
                    title = "Sample Prompt",
                    description = "Sample Prompt Description"
                )

            }) {
                Text("Authenticate")
            }

            biometricResult?.let {
                Text(
                    text = when (it) {
                        is BiometricResult.AuthenticationError -> {
                            it.error
                        }

                        BiometricResult.AuthenticationFailed -> {
                            "Authentication failed"
                        }

                        BiometricResult.AuthenticationNotSet -> {
                            "Authentication not set"
                        }

                        BiometricResult.AuthenticationSuccess -> {
                            "Authentication success"
                        }

                        BiometricResult.FeatureUnavailable -> {
                            "Feature Unavailable"
                        }

                        BiometricResult.HardwareUnavailable -> {
                            "Hardware Unavailable"
                        }
                    }
                )
                /*if (it.success) {

                } else {
                    Text(text = "Authentication failed: ${it.message}")
                }*/
            }
        }
    }
}
