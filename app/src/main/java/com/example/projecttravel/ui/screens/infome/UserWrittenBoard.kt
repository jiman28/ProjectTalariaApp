//package com.example.projecttravel.ui.screens.infome
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.projecttravel.R
//import com.example.projecttravel.data.uistates.BoardPageUiState
//import com.example.projecttravel.data.uistates.UserUiState
//import com.example.projecttravel.model.Board
//import com.example.projecttravel.model.Company
//import com.example.projecttravel.model.Trade
//import com.example.projecttravel.ui.screens.boardlist.BoardsPageTabButtons
//import com.example.projecttravel.ui.screens.infome.tempboardlist.ListBoard
//import com.example.projecttravel.ui.screens.infome.tempboardlist.ListCompany
//import com.example.projecttravel.ui.screens.infome.tempboardlist.ListTrade
//import com.example.projecttravel.ui.screens.boardlist.NoArticlesFoundScreen
//import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
//import com.example.projecttravel.data.repositories.board.viewmodels.BoardUiState
//import com.example.projecttravel.data.repositories.board.viewmodels.CompanyUiState
//import com.example.projecttravel.data.repositories.board.viewmodels.TradeUiState
//
//@Composable
//fun UserWrittenBoard(
//    userUiState: UserUiState,
//    boardPageUiState: BoardPageUiState,
//    filteredBoardList: List<Board>?,
//    filteredCompanyList: List<Company>?,
//    filteredTradeList: List<Trade>?,
//    boardPageViewModel: BoardPageViewModel,
//    onBoardClicked: () -> Unit,
//) {
//    Column (
//        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
//        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
//    ) {
//        Column {
//            BoardsPageTabButtons(
//                boardPageUiState = boardPageUiState,
//                boardPageViewModel = boardPageViewModel,
//            )
//        }
//
//        Spacer(modifier = Modifier.padding(2.dp))
//        /** Lists Show ====================*/
//        Column {
//            val currentUserMenuId = userUiState.checkOtherUser?.id
//            if (currentUserMenuId != null) {
//                // 필터링을 거친 리스트
//                when (boardPageUiState.currentSelectedBoardTab) {
//                    R.string.boardTabTitle -> {
//                        if (filteredBoardList != null) {
//                            if (filteredBoardList.isNotEmpty()) {
//                                ListBoard(
//                                    boardUiState = BoardUiState.BoardSuccess(filteredBoardList),
//                                    boardPageViewModel = boardPageViewModel,
//                                    onBoardClicked = onBoardClicked,
//                                    contentPadding = PaddingValues(0.dp),
//                                )
//                            } else {
//                                NoArticlesFoundScreen()
//                            }
//                        } else {
//                            NoArticlesFoundScreen()
//                        }
//                    }
//
//                    R.string.companyTabTitle -> {
//                        if (filteredCompanyList != null) {
//                            if (filteredCompanyList.isNotEmpty()) {
//                                ListCompany(
//                                    companyUiState = CompanyUiState.CompanySuccess(filteredCompanyList),
//                                    boardPageViewModel = boardPageViewModel,
//                                    onBoardClicked = onBoardClicked,
//                                    contentPadding = PaddingValues(0.dp),
//                                )
//                            } else {
//                                NoArticlesFoundScreen()
//                            }
//                        } else {
//                            NoArticlesFoundScreen()
//                        }
//                    }
//
//                    R.string.tradeTabTitle -> {
//                        if (filteredTradeList != null) {
//                            if (filteredTradeList.isNotEmpty()) {
//                                ListTrade(
//                                    tradeUiState = TradeUiState.TradeSuccess(filteredTradeList),
//                                    boardPageViewModel = boardPageViewModel,
//                                    onBoardClicked = onBoardClicked,
//                                    contentPadding = PaddingValues(0.dp),
//                                )
//                            } else {
//                                NoArticlesFoundScreen()
//                            }
//                        } else {
//                            NoArticlesFoundScreen()
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
