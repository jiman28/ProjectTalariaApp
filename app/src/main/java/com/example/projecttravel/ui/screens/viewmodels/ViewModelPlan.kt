package com.example.projecttravel.ui.screens.viewmodels

import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.select.TourAttractionAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class ViewModelPlan : ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(PlanUiState())
    val planUiState: StateFlow<PlanUiState> = _uiState.asStateFlow()

    /** setDateRange Object */
    fun setPlanDateRange(desiredDateRange: ClosedRange<LocalDate>?) {
        _uiState.update { currentState ->
            currentState.copy(planDateRange = desiredDateRange)
        }
    }

    /** setDateRange Object */
    fun setPlanTourAttr(desiredTourAttraction: List<TourAttractionAll>) {
        _uiState.update { currentState ->
            currentState.copy(planTourAttractionAll = desiredTourAttraction)
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

//    // 날짜를 정수에 매핑
//    private val dateToIntMap: MutableMap<LocalDate, Int> = mutableMapOf()
//
//    init {
//        planDateRange?.let { range ->
//            dateToIntMap.clear()
//            var currentDate = range.start
//            var dayCount = 1
//            while (currentDate <= range.endInclusive) {
//                dateToIntMap[currentDate] = dayCount
//                currentDate = currentDate.plusDays(1)
//                dayCount++
//            }
//        }
//    }
//
//    // 날짜를 정수로 변환하는 함수
//    fun getDateAsInt(date: LocalDate): Int? {
//        return dateToIntMap[date]
//    }
//
//    // 두 객체(LocalDate, List<TourAttractionInfo)의 내용이 같은지를 비교
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as PlanUiState
//
//        if (planDateRange != other.planDateRange) return false
//        if (dateToSelectedTourAttractions != other.dateToSelectedTourAttractions) return false
//
//        return true
//    }
//
//    // 객체를 고유하게 식별하기 위한 해시 코드를 생성
//    override fun hashCode(): Int {
//        var result = planDateRange.hashCode()
//        result = 31 * result + dateToSelectedTourAttractions.hashCode()
//        return result
//    }

}