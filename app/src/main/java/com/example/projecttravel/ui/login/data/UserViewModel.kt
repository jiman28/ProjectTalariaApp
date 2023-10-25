package com.example.projecttravel.ui.login.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel : ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    /** setName Object */
    fun setLoginEmail (email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }
}