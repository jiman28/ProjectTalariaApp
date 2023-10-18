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
) {
    val dateToSelectedTourAttractions: Map<LocalDate, List<TourAttractionAll>> = createDefaultDateToSelectedTourAttractions()
    /** Function that Distributes TourAttractions evenly according to LocalDate and sends it as a default value to dateToSelectedTourAttractions */
    private fun createDefaultDateToSelectedTourAttractions(): Map<LocalDate, List<TourAttractionAll>> {
        val result = mutableMapOf<LocalDate, List<TourAttractionAll>>()
        if (planDateRange != null && planTourAttractionAll.isNotEmpty()) {
            val dateRange = planDateRange
            val mutableAttractionList = planTourAttractionAll.toMutableList()
            val totalAttractionCount = mutableAttractionList.size
            val daysCount = dateRange.endInclusive.toEpochDay() - dateRange.start.toEpochDay() + 1
            val attractionsPerDay = totalAttractionCount / daysCount
            var remainingAttractions = totalAttractionCount % daysCount

            if (mutableAttractionList.isNotEmpty()) {
                var currentDate = dateRange.start
                while (!currentDate.isAfter(dateRange.endInclusive)) {
                    val attractionsForDate = if (remainingAttractions > 0) {
                        mutableAttractionList.take((attractionsPerDay + 1).toInt())
                            .also { remainingAttractions-- }
                    } else {
                        mutableAttractionList.take(attractionsPerDay.toInt())
                    }
                    result[currentDate] = attractionsForDate
                    mutableAttractionList.removeAll(attractionsForDate)
                    currentDate = currentDate.plusDays(1)
                }
            }
        }
        return result
    }
}
