package com.example.projecttravel.ui.screens.selection.selectapi

import kotlinx.serialization.Serializable

@Serializable
data class GetAttrWeather (
    var placeId: String,
    var finds: String,
    var selectedDate: String,
)
