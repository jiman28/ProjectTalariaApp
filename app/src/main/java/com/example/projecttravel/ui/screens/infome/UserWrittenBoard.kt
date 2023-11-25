//package com.example.projecttravel.ui.screens.infome
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.projecttravel.R
//import com.example.projecttravel.data.uistates.BoardPageUiState
//import com.example.projecttravel.data.uistates.UserUiState
//import com.example.projecttravel.ui.screens.boardlist.BoardsPageTabButtons
//import com.example.projecttravel.ui.screens.boardlist.NoArticlesFoundScreen
//import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
////import com.example.projecttravel.data.repositories.board.viewmodels.BoardUiState
//import com.example.projecttravel.data.uistates.PlanUiState
//import com.example.projecttravel.ui.screens.boardlist.ListCompanyEntity
//import com.example.projecttravel.ui.screens.boardlist.ListTradeEntity
//import com.example.projecttravel.ui.screens.boardlist.ListBoardEntity
//
//@Composable
//fun UserWrittenBoard(
//    userUiState: UserUiState,
//    planUiState: PlanUiState,
//    boardPageUiState: BoardPageUiState,
//    boardPageViewModel: BoardPageViewModel,
//    onResetButtonClicked: () -> Unit,
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
//                        if (boardPageUiState.currentBoardList != null && boardPageUiState.currentBoardList.list.isNotEmpty()) {
//                            ListBoardEntity(
//                                boardList = boardPageUiState.currentBoardList,
//                                userUiState = userUiState,
//                                planUiState = planUiState,
//                                boardPageUiState = boardPageUiState,
//                                boardPageViewModel = boardPageViewModel,
//                                onBoardClicked = onBoardClicked,
//                                onResetButtonClicked = onResetButtonClicked,
//                            )
//                        } else {
//                            NoArticlesFoundScreen()
//                        }
//                    }
//
//                    R.string.companyTabTitle -> {
//                        if (boardPageUiState.currentCompanyList != null && boardPageUiState.currentCompanyList.list.isNotEmpty()) {
//                            ListCompanyEntity(
//                                companyList = boardPageUiState.currentCompanyList,
//                                userUiState = userUiState,
//                                planUiState = planUiState,
//                                boardPageUiState = boardPageUiState,
//                                boardPageViewModel = boardPageViewModel,
//                                onBoardClicked = onBoardClicked,
//                                onResetButtonClicked = onResetButtonClicked,
//                            )
//                        } else {
//                            NoArticlesFoundScreen()
//                        }
//                    }
//
//                    R.string.tradeTabTitle -> {
//                        if (boardPageUiState.currentTradeList != null && boardPageUiState.currentTradeList.list.isNotEmpty()) {
//                            ListTradeEntity(
//                                tradeList = boardPageUiState.currentTradeList,
//                                userUiState = userUiState,
//                                planUiState = planUiState,
//                                boardPageUiState = boardPageUiState,
//                                boardPageViewModel = boardPageViewModel,
//                                onBoardClicked = onBoardClicked,
//                                onResetButtonClicked = onResetButtonClicked,
//                            )
//                        } else {
//                            NoArticlesFoundScreen()
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
