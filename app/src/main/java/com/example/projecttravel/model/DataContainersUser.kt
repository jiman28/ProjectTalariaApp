package com.example.projecttravel.model

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val email: String,
    val password: String,
)

@Serializable
data class UserResponse (
    val id: String,
    val email: String,
    val name: String,
    val picture: String?,
)

@Serializable
data class UserInfo (
    val id: String,
    val user: String,
    val reliability: String,
    val sights: String,
    val nature: String,
    val culture: String,
    val history: String,
    val food: String,
    val religion: String,
)
