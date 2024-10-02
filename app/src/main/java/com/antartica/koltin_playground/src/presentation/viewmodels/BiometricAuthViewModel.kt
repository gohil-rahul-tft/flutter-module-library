package com.antartica.koltin_playground.src.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antartica.koltin_playground.src.domain.model.BiometricData
import com.antartica.koltin_playground.src.domain.repository.BiometricRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class BiometricAuthViewModel(private val repository: BiometricRepository) :
    ViewModel() {
    private val _bioMetricData = MutableStateFlow<BiometricData?>(null)
    val bioMetricData = _bioMetricData.asStateFlow()

    fun authenticateUser() = viewModelScope.launch {
        val result = repository.authenticate()
        _bioMetricData.value = result
    }
}