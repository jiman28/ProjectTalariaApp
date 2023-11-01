package com.example.projecttravel.ui.screens.plantrip.planapi

import com.example.projecttravel.model.plan.SpotDtoResponse
import kotlinx.serialization.Serializable

@Serializable
data class SetPlan (
    var planName: String,
    var email: String,
    var startDay: String,
    var endDay: String,
    var plans: List<SpotDtoResponse>
)