package com.example.projecttravel.ui.screens.login.data

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    val id: String,
    val email: String,
    val name: String,
    val picture: String?,
)