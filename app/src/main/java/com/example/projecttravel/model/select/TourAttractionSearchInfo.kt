package com.example.projecttravel.model.select

import kotlinx.serialization.Serializable

@Serializable
data class TourAttractionSearchInfo (
    val name: String,
    val globalCode: String,
    val compoundCode: String,
    val Address: String,    // 카멜 표기법좀 제발
    val lat: String,
    val lng: String,
    val img: String,
    val inOut: String,
    val cityId: String,
): TourAttractionAll