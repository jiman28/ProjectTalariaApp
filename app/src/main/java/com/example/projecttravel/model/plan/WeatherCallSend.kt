package com.example.projecttravel.model.plan

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCallSend (
    var startDate: String,
    var endDate: String,
    var lat: String,
    var lng: String,
)