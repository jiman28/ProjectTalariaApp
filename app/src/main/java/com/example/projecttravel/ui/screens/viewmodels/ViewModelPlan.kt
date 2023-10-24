package com.example.projecttravel.ui.screens.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.select.TourAttractionAll
import com.example.projecttravel.ui.screens.selection.selectapi.SpotDto
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

    /** setName Object */
    fun setCurrentPlanDate (desiredDate: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(currentPlanDate = desiredDate)
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

    /** setDateToAttrByRandom Object */
    fun setDateToAttrByRandom(desiredTourAttraction: List<SpotDtoResponse>) {
        _uiState.update { currentState ->
            currentState.copy(dateToAttrByRandom = desiredTourAttraction)
        }
    }

    /** setPlanTourAttrMap Object */
    fun setPlanTourAttrMap(desiredTourAttraction: Map<LocalDate, List<TourAttractionAll>>) {
        _uiState.update { currentState ->
            currentState.copy(dateToSelectedTourAttrMap = desiredTourAttraction)
        }
    }

//    /** moveAttrByWeather Object */
//    fun moveAttrByWeather(destinationDate: LocalDate, spotDtoToMove: SpotDto){
//        _uiState.update { currentState ->
//            // 복사본 생성
//            val updatedAttrByWeather = currentState.dateToAttrByWeather.toMutableList()
//
//            // destinationDate에 해당하는 SpotDtoResponse 찾기
//            val destinationSpotDtoResponseWeather = updatedAttrByWeather.find { it.date == destinationDate.toString() }
//
//            if (destinationSpotDtoResponseWeather != null) {
//                destinationSpotDtoResponseWeather.list = destinationSpotDtoResponseWeather.list.toMutableList()
//                if ((destinationSpotDtoResponseWeather.list as MutableList<SpotDto>).contains(spotDtoToMove)) {
//                    Log.d("xxxxxxxxxxxxxxxxxxxx", "이미 있는 거임")
//                } else {
//                    (destinationSpotDtoResponseWeather.list as MutableList<SpotDto>).add(spotDtoToMove)
//                }
//            }
//
//            // 업데이트된 목록을 새로운 PlanUiState로 복사하여 반환
//            currentState.copy(dateToAttrByWeather = updatedAttrByWeather)
//        }
//    }


    fun removeAttrByRandom(sourceDate: LocalDate, spotDtoToMove: SpotDto) {
        _uiState.update { currentState ->
            // 복사본 생성
            val updatedAttrByRandom = currentState.dateToAttrByRandom.toMutableList()
            // sourceDate와 destinationDate에 해당하는 SpotDtoResponse 찾기
            val sourceSpotDtoResponse = updatedAttrByRandom.find { it.date == sourceDate.toString() }
            (sourceSpotDtoResponse?.list as MutableList<SpotDto>).remove(spotDtoToMove)
            // destinationSpotDtoResponse에 spotDtoToMove 추가
            currentState.copy(dateToAttrByRandom = updatedAttrByRandom)
        }
    }

    /** moveAttrByRandom Object */
    fun addAttrByRandom(destinationDate: LocalDate, spotDtoToMove: SpotDto) {
        _uiState.update { currentState ->
            // 복사본 생성
            val updatedAttrByRandom = currentState.dateToAttrByRandom.toMutableList()
            // sourceDate와 destinationDate에 해당하는 SpotDtoResponse 찾기
            val destinationSpotDtoResponse = updatedAttrByRandom.find { it.date == destinationDate.toString() }
            (destinationSpotDtoResponse?.list as MutableList<SpotDto>).add(spotDtoToMove)
            currentState.copy(dateToAttrByRandom = updatedAttrByRandom)
        }
    }


    /** reset all Objects */
    fun resetAllPlanUiState() {
        _uiState.value = PlanUiState() // DailyPlanUiState를 기본 값으로 재설정
    }
}