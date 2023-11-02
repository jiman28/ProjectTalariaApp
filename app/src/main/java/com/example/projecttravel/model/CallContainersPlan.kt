package com.example.projecttravel.model

import kotlinx.serialization.Serializable

@Serializable
data class SpotDtoResponse(
    var date: String,
    var list: List<SpotDto>
)

@Serializable
data class SpotDto (
    var pk: String,
    var name: String,
    var img: String,
    var lan: String,
    var lat: String,
    var inOut: String,
    var cityId: String,
)

@Serializable
data class WeatherCallSend (
    var startDate: String,
    var endDate: String,
    var lat: String,
    var lng: String,
)

@Serializable
data class WeatherResponseGet (
    val day: String?,
    val inOut: String?,
    val icon: String?,
)

@Serializable
data class GetAttrWeather (
    var placeId: String,
    var finds: String,
    var selectedDate: String,
)
