package com.example.projecttravel.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.SearchUiState
import com.example.projecttravel.ui.screens.searchPlaceGps.SearchedPlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelSearch : ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    /** setName Object */
    fun setSearched (desiredPlace: SearchedPlace) {
        _uiState.update { currentState ->
            currentState.copy(searchedPlace = desiredPlace)
        }
    }

    /** setErrorMsg */
    fun setErrorMsg (error: String?) {
        _uiState.update { currentState ->
            currentState.copy(errorMsg = error)
        }
    }

    /** reset all Objects */
    fun resetAllSearchUiState() {
        _uiState.value = SearchUiState() // DailyPlanUiState를 기본 값으로 재설정
    }
}
