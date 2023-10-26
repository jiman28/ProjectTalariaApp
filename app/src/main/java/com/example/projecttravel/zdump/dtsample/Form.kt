package com.example.projecttravel.zdump.dtsample

import kotlinx.serialization.Serializable

@Serializable
data class Form (
    val email: String,
    val name: String,
    val password: String,
    val type: String,
)