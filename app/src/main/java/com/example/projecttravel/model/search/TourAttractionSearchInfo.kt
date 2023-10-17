package com.example.projecttravel.model.search

import com.example.projecttravel.model.select.TourAttractionAll
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
): TourAttractionAll