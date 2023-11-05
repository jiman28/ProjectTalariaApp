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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.boards.BoardsPageTabButtons
import com.example.projecttravel.ui.screens.boards.ListBoard
import com.example.projecttravel.ui.screens.boards.ListCompany
import com.example.projecttravel.ui.screens.boards.ListTrade
import com.example.projecttravel.ui.screens.boards.NoArticlesFoundScreen
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.board.BoardUiState
import com.example.projecttravel.ui.screens.viewmodels.board.CompanyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.TradeUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListBoard
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListCompany
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListTrade

@Composable
fun UserWrittenBoard(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onBoardClicked: () -> Unit,
) {
    val boardListViewModel: ViewModelListBoard = viewModel(factory = ViewModelListBoard.BoardFactory)
    val companyListViewModel: ViewModelListCompany = viewModel(factory = ViewModelListCompany.CompanyFactory)
    val tradeListViewModel: ViewModelListTrade = viewModel(factory = ViewModelListTrade.TradeFactory)

    val boardUiState = (boardListViewModel.boardUiState as? BoardUiState.BoardSuccess)
    val companyUiState = (companyListViewModel.companyUiState as? CompanyUiState.CompanySuccess)
    val tradeUiState = (tradeListViewModel.tradeUiState as? TradeUiState.TradeSuccess)

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
                val filteredBoardList = boardUiState?.boardList?.filter { boardItem ->
                    // 여기서 boardItem은 게시글 목록의 각 항목입니다.
                    // id와 게시글의 id에 대한 일치 여부를 확인합니다.
                    val idTag = boardItem.userId
                    val boardMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                    // id가 일치하면 true를 반환합니다.
                    boardMatchesId
                }
                val filteredCompanyList = companyUiState?.companyList?.filter { companyItem ->
                    // 여기서 companyItem은 게시글 목록의 각 항목입니다.
                    // id와 게시글의 id에 대한 일치 여부를 확인합니다.
                    val idTag = companyItem.userId
                    val companyMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                    // id가 일치하면 true를 반환합니다.
                    companyMatchesId
                }

                val filteredTradeList = tradeUiState?.tradeList?.filter { tradeItem ->
                    // 여기서 tradeItem은 게시글 목록의 각 항목입니다.
                    // id와 게시글의 id에 대한 일치 여부를 확인합니다.
                    val idTag = tradeItem.userId
                    val tradeMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                    // id가 일치하면 true를 반환합니다.
                    tradeMatchesId
                }

                // 필터링을 거친 리스트
                when (boardSelectUiState.currentSelectedBoard) {
                    R.string.board -> {
                        if (boardUiState != null) {
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
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }

                    R.string.company -> {
                        if (companyUiState != null) {
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
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }

                    R.string.trade -> {
                        if (tradeUiState != null) {
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
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }
//                    else -> {
//                        CheckReply(
//                            contentPadding = PaddingValues(0.dp),
//                        )
//                    }
                }

            }
        }
    }

}









