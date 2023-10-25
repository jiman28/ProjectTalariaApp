package com.example.projecttravel.ui.login.data

import kotlinx.serialization.Serializable

@Serializable
data class Form (
    val email: String,
    val name: String,
    val password: String,
    val type: String,
)