package com.example.projecttravel.data.uistates

import com.example.projecttravel.model.select.TourAttractionAll
import com.example.projecttravel.ui.screens.selection.selectapi.SpotDtoResponse
import com.example.projecttravel.ui.screens.selection.selectapi.WeatherResponseGet
import java.time.LocalDate

data class PlanUiState(
    val planDateRange: ClosedRange<LocalDate>? = null,
    val planTourAttractionAll: List<TourAttractionAll> = emptyList(),
    val dateToWeather: List<WeatherResponseGet> = emptyList(),
    val dateToAttrByWeather: List<SpotDtoResponse> = emptyList(),
    val dateToAttrByRandom: List<SpotDtoResponse> = emptyList(),
    val dateToSelectedTourAttrMap: Map<LocalDate, List<TourAttractionAll>> = emptyMap(),
)
