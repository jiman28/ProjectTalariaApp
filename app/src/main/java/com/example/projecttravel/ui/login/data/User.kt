package com.example.projecttravel.ui.login.data

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val email: String,
    val password: String,
)