package com.example.projecttravel.data.viewmodels

import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.BoardEntity
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.CompanyEntity
import com.example.projecttravel.model.CompanyList
import com.example.projecttravel.model.ReplyList
import com.example.projecttravel.model.Trade
import com.example.projecttravel.model.TradeEntity
import com.example.projecttravel.model.TradeList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoardPageViewModel: ViewModel() {
    /** all selection */
    private val _uiState = MutableStateFlow(BoardPageUiState())
    val boardPageUiState: StateFlow<BoardPageUiState> = _uiState.asStateFlow()

    /** setWriteBoardMenu Object */
    fun setWriteBoardMenu(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedWriteBoardMenu = desiredBoard)
        }
    }

    /** setSelectedBoard Object */
    fun setSelectedBoard(desiredBoard: BoardEntity) {
        _uiState.update { currentState ->
            currentState.copy(selectedBoardContent = desiredBoard)
        }
    }

    /** setSelectedCompany Object */
    fun setSelectedCompany(desiredBoard: CompanyEntity) {
        _uiState.update { currentState ->
            currentState.copy(selectedCompanyContent = desiredBoard)
        }
    }

    /** setSelectedTrade Object */
    fun setSelectedTrade(desiredBoard: TradeEntity) {
        _uiState.update { currentState ->
            currentState.copy(selectedTradeContent = desiredBoard)
        }
    }

    /** setCurrentBoardState Object */
    fun setCurrentSelectedBoard(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentSelectedBoard = desiredBoard)
        }
    }

    /** setCurrentBoardState Object */
    fun setCurrentSearchKeyWord(kw: String) {
        _uiState.update { currentState ->
            currentState.copy(currentSearchKeyWord = kw)
        }
    }

    /** setCurrentBoardState Object */
    fun setCurrentSearchType(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentSearchType = desiredBoard)
        }
    }

    /** setWriteBoardMenu Object */
    fun setBoardList(lists: BoardList) {
        _uiState.update { currentState ->
            currentState.copy(currentBoardList = lists)
        }
    }

    fun setCompanyList(lists: CompanyList) {
        _uiState.update { currentState ->
            currentState.copy(currentCompanyList = lists)
        }
    }

    fun setTradeList(lists: TradeList) {
        _uiState.update { currentState ->
            currentState.copy(currentTradeList = lists)
        }
    }

    /** setWriteBoardMenu Object */
    fun setReplyList(lists: List<ReplyList>) {
        _uiState.update { currentState ->
            currentState.copy(currentReplyList = lists)
        }
    }

    // for MyPage=====================================

    /** setSelectedBoard Object */
    fun setMyBoard(desiredBoard: Board) {
        _uiState.update { currentState ->
            currentState.copy(myBoardContent = desiredBoard)
        }
    }

    /** setSelectedCompany Object */
    fun setMyCompany(desiredBoard: Company) {
        _uiState.update { currentState ->
            currentState.copy(myCompanyContent = desiredBoard)
        }
    }

    /** setSelectedTrade Object */
    fun setMyTrade(desiredBoard: Trade) {
        _uiState.update { currentState ->
            currentState.copy(myTradeContent = desiredBoard)
        }
    }
}