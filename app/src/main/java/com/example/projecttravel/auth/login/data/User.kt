package com.example.projecttravel.auth.login.data

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val email: String,
    val password: String,
)