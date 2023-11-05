package com.example.projecttravel.data.uistates

import com.example.projecttravel.model.SendSignIn
import com.example.projecttravel.model.UserResponse

data class UserUiState(
    val currentLogin: UserResponse? = null,
    val currentSignIn: SendSignIn? =null,
    val checkLikeUsers: List<UserResponse> = emptyList(),
    val checkOtherUser: UserResponse? = null,
    val isBackHandlerClick: Boolean = false,
)
