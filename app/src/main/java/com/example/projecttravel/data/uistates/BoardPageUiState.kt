package com.example.projecttravel.data.uistates

import com.example.projecttravel.R
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

data class BoardPageUiState(

    val selectedWriteBoardMenu: Int = R.string.selectTabTitle,

    val selectedBoardContent: BoardEntity? = null,
    val selectedCompanyContent: CompanyEntity? = null,
    val selectedTradeContent: TradeEntity? = null,

    val currentSelectedBoard: Int = R.string.boardTabTitle,
    val currentSearchKeyWord: String = "",
    val currentSearchType: Int = R.string.selectSearchType,

    val currentBoardList: BoardList? = null,
    val currentCompanyList: CompanyList? = null,
    val currentTradeList: TradeList? = null,

    val currentReplyList: List<ReplyList> = emptyList(),

    // for MyPage
    val myBoardContent: Board? = null,
    val myCompanyContent: Company? = null,
    val myTradeContent: Trade? = null,
)