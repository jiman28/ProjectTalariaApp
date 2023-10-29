package com.example.projecttravel.model.user

import kotlinx.serialization.Serializable

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
