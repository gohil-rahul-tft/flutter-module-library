package com.antartica.koltin_playground.src.data.repository

import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.antartica.koltin_playground.src.domain.model.BiometricData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class BiometricManager(private val activity: AppCompatActivity) {
    private val executor = ContextCompat.getMainExecutor(activity)
    private val biometricManager = from(activity)

    private val resultChannel = Channel<BiometricResult>()
    val promptResults = resultChannel.receiveAsFlow()

    private val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric Authentication")
        .setDescription("Place your finger on the sensor or look at the front camera to authenticate.")
        .setNegativeButtonText("Cancel")
        .setAllowedAuthenticators(BIOMETRIC_STRONG)
        .build()

    // Not being used
    suspend fun authenticateBiometric(): BiometricData {
        return suspendCancellableCoroutine { continuation ->
            val biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        Log.d("TAG", "Authentication successful!!!")
                        continuation.resume(BiometricData(success = true))
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        Log.e("TAG", "onAuthenticationError: $errString")
                        continuation.resume(
                            BiometricData(
                                success = false,
                                message = errString.toString()
                            )
                        )
                    }

                    override fun onAuthenticationFailed() {
                        Log.e("TAG", "onAuthenticationFailed")
                        continuation.resume(
                            BiometricData(
                                success = false,
                                message = "Authentication failed"
                            )
                        )
                    }
                })

            biometricPrompt.authenticate(biometricPromptInfo)

            continuation.invokeOnCancellation {
                biometricPrompt.cancelAuthentication()
            }
        }
    }

    fun showBiometricPrompt(
        title: String,
        description: String,
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val authenticators =
            if (Build.VERSION.SDK_INT >= 30) BIOMETRIC_STRONG or DEVICE_CREDENTIAL
            else BIOMETRIC_STRONG


        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)

        if (Build.VERSION.SDK_INT < 30) {
            biometricPromptInfo.setNegativeButtonText("Cancel")
        }


        if (!canAuthenticateBiometric(authenticators)) {
            return
        }


        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Log.d("TAG", "Authentication successful!!!")
                    resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Log.e("TAG", "onAuthenticationError: $errString")
                    resultChannel.trySend(
                        BiometricResult.AuthenticationError(
                            errString.toString()
                        )
                    )
                }

                override fun onAuthenticationFailed() {
                    Log.e("TAG", "onAuthenticationFailed")
                    resultChannel.trySend(
                        BiometricResult.AuthenticationFailed
                    )
                }
            },
        )

        biometricPrompt.authenticate(biometricPromptInfo.build())

    }


    private fun canAuthenticateBiometric(authenticator: Int): Boolean {
        return when (biometricManager.canAuthenticate(authenticator)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(BiometricResult.HardwareUnavailable)
                return false
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(BiometricResult.FeatureUnavailable)
                return false
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(BiometricResult.AuthenticationNotSet)
                return false
            }

            else -> true
        }
    }

    sealed interface BiometricResult {
        data object HardwareUnavailable : BiometricResult
        data object FeatureUnavailable : BiometricResult
        data class AuthenticationError(val error: String) : BiometricResult
        data object AuthenticationFailed : BiometricResult
        data object AuthenticationSuccess : BiometricResult
        data object AuthenticationNotSet : BiometricResult
    }
}
