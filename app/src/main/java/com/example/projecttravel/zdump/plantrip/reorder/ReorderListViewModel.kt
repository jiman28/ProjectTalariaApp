package com.example.projecttravel.zdump.plantrip.reorder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.selection.selectapi.SpotDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition

class ReorderListViewModel : ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(PlanUiState())
    val planUiState: StateFlow<PlanUiState> = _uiState.asStateFlow()

    var selectedDateAttrs by mutableStateOf<List<SpotDto>>(emptyList())

    init {
        viewModelScope.launch {
            planUiState.collect { newState ->
                // planUiState가 변경될 때마다 selectedDateAttrs 업데이트
                selectedDateAttrs = computeSelectedDateAttrs(newState)
            }
        }
    }

    private fun computeSelectedDateAttrs(planUiState: PlanUiState): List<SpotDto> {
        val selectedDate = planUiState.currentPlanDate.toString()
        return planUiState.dateToAttrByRandom.find { it.date == selectedDate }?.list ?: emptyList()
    }

    fun moveAttr(from: ItemPosition, to: ItemPosition) {
        selectedDateAttrs = selectedDateAttrs.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

}