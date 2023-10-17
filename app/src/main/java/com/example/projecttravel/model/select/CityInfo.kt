package com.example.projecttravel.model.select

import kotlinx.serialization.Serializable

@Serializable
data class CityInfo (
    val cityId: String,
    val cityName: String,
    val countryId: String,
)