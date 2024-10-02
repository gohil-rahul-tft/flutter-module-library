package com.antartica.koltin_playground.src.data.repository

import com.antartica.koltin_playground.src.domain.model.BiometricData
import com.antartica.koltin_playground.src.domain.repository.BiometricRepository


class BiometricRepositoryImpl(private val biometricManager: BiometricManager) : BiometricRepository {
    override suspend fun authenticate(): BiometricData {
        val result = biometricManager.authenticateBiometric()
        return result
    }

}