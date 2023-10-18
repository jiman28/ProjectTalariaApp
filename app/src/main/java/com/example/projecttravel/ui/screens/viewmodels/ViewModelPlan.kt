package com.example.projecttravel.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.select.TourAttractionAll
import com.example.projecttravel.ui.screens.selection.selectapi.SpotDtoResponse
import com.example.projecttravel.ui.screens.selection.selectapi.WeatherResponseGet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Response
import java.time.LocalDate

class ViewModelPlan : ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(PlanUiState())
    val planUiState: StateFlow<PlanUiState> = _uiState.asStateFlow()

    /** setPlanDateRange Object */
    fun setPlanDateRange(desiredDateRange: ClosedRange<LocalDate>?) {
        _uiState.update { currentState ->
            currentState.copy(planDateRange = desiredDateRange)
        }
    }

    /** setPlanTourAttr Object */
    fun setPlanTourAttr(desiredTourAttraction: List<TourAttractionAll>) {
        _uiState.update { currentState ->
            currentState.copy(planTourAttractionAll = desiredTourAttraction)
        }
    }

    /** setDateToWeather Object */
    fun setDateToWeather(desiredTourAttraction: List<WeatherResponseGet>) {
        _uiState.update { currentState ->
            currentState.copy(dateToWeather = desiredTourAttraction)
        }
    }

    /** setDateToAttrByWeather Object */
    fun setDateToAttrByWeather(desiredTourAttraction: List<SpotDtoResponse>) {
        _uiState.update { currentState ->
            currentState.copy(dateToAttrByWeather = desiredTourAttraction)
        }
    }

    /** reset all Objects */
    fun resetAllPlanUiState() {
        _uiState.value = PlanUiState() // DailyPlanUiState를 기본 값으로 재설정
    }

//    @RequiresApi(34)
//    fun createDefaultDateToSelectedTourAttractions(): Map<LocalDate, List<TourAttractionAll>> {
//        val currentState = _uiState.value
//        val result = mutableMapOf<LocalDate, List<TourAttractionAll>>()
//        if (currentState.planDateRange != null && currentState.planTourAttractionAll.isNotEmpty()) {
//            val dateRange = currentState.planDateRange
//
//            val tourAttractions = currentState.planTourAttractionAll.toMutableList()
//            dateRange.start.datesUntil(dateRange.endInclusive.plusDays(1))?.forEach { date ->
//                val attractionsForDate = tourAttractions.take(dateRangeCounter.count())
//                result[date] = attractionsForDate
//                tourAttractions.removeAll(attractionsForDate)
//            }
//        }
//        return result
//    }

//    /** SETTING List<TourAttractionAll> */
//    /** add TourAttraction Object */
//    fun addPlanTourAttraction(desiredTourAttraction: TourAttractionInfo) {
//        _uiState.update { currentState ->
//            val updatedList = currentState.planTourAttractionAll.toMutableList()
//            if (!updatedList.contains(desiredTourAttraction)) {
//                updatedList.add(desiredTourAttraction)
//                currentState.copy(planTourAttractionAll = updatedList)
//            } else {
//                currentState // 이미 추가된 객체이므로 상태를 변경하지 않음
//            }
//        }
//    }
//
//    /** add TourAttrSearch Object */
//    fun addPlanTourAttrSearch(desiredTourAttraction: TourAttractionSearchInfo) {
//        _uiState.update { currentState ->
//            val updatedList = currentState.planTourAttractionAll.toMutableList()
//            if (!updatedList.contains(desiredTourAttraction)) {
//                updatedList.add(desiredTourAttraction)
//                currentState.copy(planTourAttractionAll = updatedList)
//            } else {
//                currentState // 이미 추가된 객체이므로 상태를 변경하지 않음
//            }
//        }
//    }
}