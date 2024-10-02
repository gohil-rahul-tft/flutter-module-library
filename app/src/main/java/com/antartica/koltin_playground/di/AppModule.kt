package com.antartica.koltin_playground.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*@Provides
    fun provideBiometricManager(@ActivityContext context: AppCompatActivity): BiometricManager {
        return BiometricManager(context)
    }*/

    /*@Provides
    fun provideBiometricRepository(biometricManager: BiometricManager): BiometricRepository {
        return BiometricRepositoryImpl(biometricManager)
    }*/
}

