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

    /** setPlanTourAttrMap Object */
    fun setPlanTourAttrMap(desiredTourAttraction: Map<LocalDate, List<TourAttractionAll>>) {
        _uiState.update { currentState ->
            currentState.copy(dateToSelectedTourAttrMap = desiredTourAttraction)
        }
    }

    /** setPlanTourAttrSpot Object */
    fun setPlanTourAttrSpot(desiredTourAttraction: List<SpotDtoResponse>) {
        _uiState.update { currentState ->
            currentState.copy(dateToAttrByRandom = desiredTourAttraction)
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

//    /** Replace attraction by drag and drop */
//    fun moveAttraction(fromDate: LocalDate, toDate: LocalDate, attraction: TourAttractionAll) {
//        _uiState.update { currentState ->
//            val currentAttractions = currentState.dateToSelectedTourAttractions[fromDate]?.toMutableList() ?: return@update currentState
//
//            // Remove the attraction from the source date
//            currentAttractions.remove(attraction)
//
//            // Add the attraction to the target date
//            val targetAttractions = (currentState.dateToSelectedTourAttractions[toDate]?.toMutableList() ?: mutableListOf()).also {
//                it.add(attraction)
//            }
//
//            // Create an updated map with the modified data
//            val updatedDateToSelectedTourAttractions = currentState.dateToSelectedTourAttractions.toMutableMap()
//            updatedDateToSelectedTourAttractions[fromDate] = currentAttractions
//            updatedDateToSelectedTourAttractions[toDate] = targetAttractions
//
//            // Create and return an updated PlanUiState with the new map
//            currentState.copy(dateToSelectedTourAttractions = updatedDateToSelectedTourAttractions)
//        }
//    }
}