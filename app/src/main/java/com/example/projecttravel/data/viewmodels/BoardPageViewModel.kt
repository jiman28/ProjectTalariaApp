package com.example.projecttravel.data.viewmodels

import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.Trade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoardPageViewModel: ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(BoardPageUiState())
    val boardPageUiState: StateFlow<BoardPageUiState> = _uiState.asStateFlow()

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

    /** setWriteBoardMenu Object */
    fun setWriteBoardMenu(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedWriteBoardMenu = desiredBoard)
        }
    }

    /** setWriteBoardMenu Object */
    fun setBoardList(lists: BoardList) {
        _uiState.update { currentState ->
            currentState.copy(currentBoardList = lists)
        }
    }
}