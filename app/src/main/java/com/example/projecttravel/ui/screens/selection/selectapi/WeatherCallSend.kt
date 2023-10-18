package com.example.projecttravel.ui.screens.selection.selectapi

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCallSend (
    var startDate: String,
    var endDate: String,
    var lat: String,
    var lng: String,
)