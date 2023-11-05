package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.Trade
import com.example.projecttravel.ui.screens.boards.BoardsPageTabButtons
import com.example.projecttravel.ui.screens.boards.ListBoard
import com.example.projecttravel.ui.screens.boards.ListCompany
import com.example.projecttravel.ui.screens.boards.ListTrade
import com.example.projecttravel.ui.screens.boards.NoArticlesFoundScreen
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.board.BoardUiState
import com.example.projecttravel.ui.screens.viewmodels.board.CompanyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.TradeUiState

@Composable
fun UserWrittenBoard(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    filteredBoardList: List<Board>?,
    filteredCompanyList: List<Company>?,
    filteredTradeList: List<Trade>?,
    boardSelectViewModel: ViewModelBoardSelect,
    onBoardClicked: () -> Unit,
) {
    Column (
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Column {
            BoardsPageTabButtons(
                boardSelectUiState = boardSelectUiState,
                boardSelectViewModel = boardSelectViewModel,
            )
        }

        Spacer(modifier = Modifier.padding(2.dp))
        /** Lists Show ====================*/
        Column {
            val currentUserMenuId = userUiState.checkOtherUser?.id
            if (currentUserMenuId != null) {
                // 필터링을 거친 리스트
                when (boardSelectUiState.currentSelectedBoard) {
                    R.string.board -> {
                        if (filteredBoardList != null) {
                            if (filteredBoardList.isNotEmpty()) {
                                ListBoard(
                                    boardUiState = BoardUiState.BoardSuccess(filteredBoardList),
                                    boardSelectViewModel = boardSelectViewModel,
                                    onBoardClicked = onBoardClicked,
                                    contentPadding = PaddingValues(0.dp),
                                )
                            } else {
                                NoArticlesFoundScreen()
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }

                    R.string.company -> {
                        if (filteredCompanyList != null) {
                            if (filteredCompanyList.isNotEmpty()) {
                                ListCompany(
                                    companyUiState = CompanyUiState.CompanySuccess(filteredCompanyList),
                                    boardSelectViewModel = boardSelectViewModel,
                                    onBoardClicked = onBoardClicked,
                                    contentPadding = PaddingValues(0.dp),
                                )
                            } else {
                                NoArticlesFoundScreen()
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }

                    R.string.trade -> {
                        if (filteredTradeList != null) {
                            if (filteredTradeList.isNotEmpty()) {
                                ListTrade(
                                    tradeUiState = TradeUiState.TradeSuccess(filteredTradeList),
                                    boardSelectViewModel = boardSelectViewModel,
                                    onBoardClicked = onBoardClicked,
                                    contentPadding = PaddingValues(0.dp),
                                )
                            } else {
                                NoArticlesFoundScreen()
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }
                }
            }
        }
    }
}
