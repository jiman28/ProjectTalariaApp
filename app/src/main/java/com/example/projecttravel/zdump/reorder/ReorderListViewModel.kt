package com.example.projecttravel.zdump.reorder

//class ReorderListViewModel : ViewModel() {
//    /** all selection */
//    private val _uiState = MutableStateFlow(PlanUiState())
//    val planUiState: StateFlow<PlanUiState> = _uiState.asStateFlow()
//
//    var selectedDateAttrs by mutableStateOf<List<SpotDto>>(emptyList())
//
//    init {
//        viewModelScope.launch {
//            planUiState.collect { newState ->
//                // planUiState가 변경될 때마다 selectedDateAttrs 업데이트
//                selectedDateAttrs = computeSelectedDateAttrs(newState)
//            }
//        }
//    }
//
//    private fun computeSelectedDateAttrs(planUiState: PlanUiState): List<SpotDto> {
//        val selectedDate = planUiState.currentPlanDate.toString()
//        return planUiState.dateToAttrByRandom.find { it.date == selectedDate }?.list ?: emptyList()
//    }
//
//    fun moveAttr(from: ItemPosition, to: ItemPosition) {
//        selectedDateAttrs = selectedDateAttrs.toMutableList().apply {
//            add(to.index, removeAt(from.index))
//        }
//    }
//
//}