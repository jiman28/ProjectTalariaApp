package com.example.projecttravel.ui.screens.login.data

import android.window.BackEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelUser : ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    /** setName Object */
    fun setUser (currentUser: UserResponse) {
        _uiState.update { currentState ->
            currentState.copy(currentLogin = currentUser)
        }
    }

    /** setName Object */
    fun setBackHandlerClick (backClick: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isBackHandlerClick = backClick)
        }
    }

    /** reset all Objects */
    fun resetUser() {
        _uiState.value = UserUiState() // UserUiState를 기본 값(null)으로 재설정
    }

}