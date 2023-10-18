package com.example.projecttravel.ui.screens.selection.selectapi

import kotlinx.serialization.Serializable

@Serializable
data class SpotDtoResponse(
    var date: String,
    var list: List<SpotDto>
)