package com.example.projecttravel.data.uistates

import com.example.projecttravel.R
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.Trade

data class BoardPageUiState(
    val currentSelectedBoard: Int = R.string.board,
    val selectedWriteBoardMenu: Int = R.string.selectMenu,
    val selectedBoardContent: Board? = null,
    val selectedTradeContent: Trade? = null,
    val selectedCompanyContent: Company? = null,

    val currentBoardList: BoardList? = null,
)