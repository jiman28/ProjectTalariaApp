package com.example.projecttravel.data.uistates

import com.example.projecttravel.R
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

data class BoardPageUiState(

    /** for Search ============================== */
    val currentSearchKeyWord: String = "",
    val currentBoardPage: Int = 0,
    val currentSearchType: Int = R.string.selectSearchType,
    val currentSearchUser: String = "",

    /** for View Article ============================== */
    val currentSelectedBoardTab: Int = R.string.boardTabTitle,
    val currentSelectedArtcNum: String = "",
    val selectedViewBoard: AllBoardsEntity? = null,

    val selectedBoardContent: BoardEntity? = null,
    val selectedCompanyContent: CompanyEntity? = null,
    val selectedTradeContent: TradeEntity? = null,

    /** for ~~ ============================== */
    /** for ~~ ============================== */
    /** for ~~ ============================== */

    val selectedWriteBoardMenu: Int = R.string.selectTabTitle,





    val currentBoardList: BoardList? = null,
    val currentCompanyList: CompanyList? = null,
    val currentTradeList: TradeList? = null,

    val currentReplyList: List<ReplyList> = emptyList(),

    // for MyPage
    val myBoardContent: Board? = null,
    val myCompanyContent: Company? = null,
    val myTradeContent: Trade? = null,
)