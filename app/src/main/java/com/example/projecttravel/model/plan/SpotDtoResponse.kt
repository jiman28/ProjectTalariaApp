package com.example.projecttravel.model.plan

import com.example.projecttravel.model.plan.SpotDto
import kotlinx.serialization.Serializable

@Serializable
data class SpotDtoResponse(
    var date: String,
    var list: List<SpotDto>
)
