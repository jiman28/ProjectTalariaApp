package com.example.projecttravel.ui.screens.selection.selectapi

import kotlinx.serialization.Serializable

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
