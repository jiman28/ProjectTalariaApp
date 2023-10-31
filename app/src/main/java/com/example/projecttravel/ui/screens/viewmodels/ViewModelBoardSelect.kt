package com.example.projecttravel.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.model.board.Board
import com.example.projecttravel.model.board.Company
import com.example.projecttravel.model.board.Trade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelBoardSelect: ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(BoardSelectUiState())
    val boardSelectUiState: StateFlow<BoardSelectUiState> = _uiState.asStateFlow()

    /** setCurrentBoardState Object */
    fun setCurrent(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentSelectedBoard = desiredBoard)
        }
    }

    /** setSelectedBoard Object */
    fun setSelectedBoard(desiredBoard: Board) {
        _uiState.update { currentState ->
            currentState.copy(selectedBoardContent = desiredBoard)
        }
    }

    /** setSelectedCompany Object */
    fun setSelectedCompany(desiredBoard: Company) {
        _uiState.update { currentState ->
            currentState.copy(selectedCompanyContent = desiredBoard)
        }
    }

    /** setSelectedTrade Object */
    fun setSelectedTrade(desiredBoard: Trade) {
        _uiState.update { currentState ->
            currentState.copy(selectedTradeContent = desiredBoard)
        }
    }
}