package com.example.projecttravel.model.select

import kotlinx.serialization.Serializable

@Serializable
data class TourAttractionInfo (
    val placeId: String,
    val imageP: String,
    val inOut: String,
    val lan: String,
    val lat: String,
    val placeAddress: String,
    val placeName: String,
    val placeType: String,
    val price: String,
    val cityId: String,
    val interestId: String,
): TourAttractionAll
