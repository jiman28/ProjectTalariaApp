package com.example.projecttravel.model.plan

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseGet (
    val day: String?,
    val inOut: String?,
    val icon: String?,
)