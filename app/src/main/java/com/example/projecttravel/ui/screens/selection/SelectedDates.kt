package com.example.projecttravel.ui.screens.selection

import kotlinx.serialization.Serializable

@Serializable
data class SelectedDates (
    val startDate: String,
    val endEndDate: String,
)