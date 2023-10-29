package com.example.projecttravel.ui.screens.login.data

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val email: String,
    val password: String,
)