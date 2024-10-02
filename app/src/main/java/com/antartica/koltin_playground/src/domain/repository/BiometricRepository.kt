package com.antartica.koltin_playground.src.domain.repository

import com.antartica.koltin_playground.src.domain.model.BiometricData

interface BiometricRepository {
    suspend fun authenticate(): BiometricData
}