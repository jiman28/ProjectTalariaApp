package com.example.projecttravel.data.uistates.viewmodels

import androidx.lifecycle.ViewModel
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.AllBoardsEntity
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

    /** for Search ============================== */
    fun setSearchKeyWord(kw: String) {
        _uiState.update { currentState ->
            currentState.copy(currentSearchKeyWord = kw)
        }
    }

    fun setSearchType(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentSearchType = desiredBoard)
        }
    }

    fun setSearchUser(email: String) {
        _uiState.update { currentState ->
            currentState.copy(currentSearchUser = email)
        }
    }

    fun setBoardPage(page: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentBoardPage = page)
        }
    }

    fun setCompanyPage(page: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentCompanyPage = page)
        }
    }

    fun setTradePage(page: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentTradePage = page)
        }
    }

    /** for View Article ============================== */
    fun setBoardTab(tab: Int) {
        _uiState.update { currentState ->
            currentState.copy(currentSelectedBoardTab = tab)
        }
    }

    fun setSelectedArtcNum(desiredBoard: String) {
        _uiState.update { currentState ->
            currentState.copy(currentSelectedArtcNum = desiredBoard)
        }
    }

    fun setViewBoard(viewBoard: AllBoardsEntity) {
        _uiState.update { currentState ->
            currentState.copy(selectedViewBoard = viewBoard)
        }
    }

    /** for ~~ ============================== */
    /** for ~~ ============================== */
    /** for ~~ ============================== */

    fun setWriteBoardMenu(desiredBoard: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedWriteBoardMenu = desiredBoard)
        }
    }

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

    fun setReplyList(lists: List<ReplyList>) {
        _uiState.update { currentState ->
            currentState.copy(currentReplyList = lists)
        }
    }

    // for MyPage=====================================

    fun setMyBoard(desiredBoard: Board) {
        _uiState.update { currentState ->
            currentState.copy(myBoardContent = desiredBoard)
        }
    }

    fun setMyCompany(desiredBoard: Company) {
        _uiState.update { currentState ->
            currentState.copy(myCompanyContent = desiredBoard)
        }
    }

    fun setMyTrade(desiredBoard: Trade) {
        _uiState.update { currentState ->
            currentState.copy(myTradeContent = desiredBoard)
        }
    }
}