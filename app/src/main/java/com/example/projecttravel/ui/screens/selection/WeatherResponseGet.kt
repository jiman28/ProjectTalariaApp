package com.example.projecttravel.ui.screens.selection

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseGet (
    val temperature: Double,
    val weatherDescription: String
)