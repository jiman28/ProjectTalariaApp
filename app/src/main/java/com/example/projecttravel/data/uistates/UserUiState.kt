package com.example.projecttravel.data.uistates

import com.example.projecttravel.model.UserResponse

data class UserUiState(
    val currentLogin: UserResponse? = null,
    val isBackHandlerClick: Boolean = false,
)
