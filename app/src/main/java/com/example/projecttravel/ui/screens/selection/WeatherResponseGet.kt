package com.example.projecttravel.ui.screens.selection

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseGet (
    val day: String?,
    val inOut: String?,
    val icon: String?,
)