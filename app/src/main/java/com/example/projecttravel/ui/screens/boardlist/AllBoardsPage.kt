package com.example.projecttravel.ui.screens.boardlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.repositories.board.viewmodels.BoardUiState
import com.example.projecttravel.data.repositories.board.viewmodels.BoardViewModel
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.ui.screens.boardview.ListBoardEntity
import com.example.projecttravel.ui.screens.boardview.ShowListBoard

@Composable
fun AllBoardsPage(
    userUiState: UserUiState,
    planUiState: PlanUiState,
//    boardPageUiState: BoardPageUiState,
//    boardPageViewModel: BoardPageViewModel,
    boardViewModel: BoardViewModel,
    boardUiState: BoardUiState,
    onBoardClicked: () -> Unit,
    onWriteButtonClicked: () -> Unit,
    onResetButtonClicked: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(10.dp) // 원하는 여백을 추가(start = 15.dp, end = 15.dp, ...)
            .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
    ) {
        /** Board Write&Search Buttons ====================*/
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            BoardWriteSearchButton(
                boardViewModel = boardViewModel,
                boardUiState = boardUiState,
                onWriteButtonClicked = onWriteButtonClicked,
                onResetButtonClicked = onResetButtonClicked,
                )
        }

        /** Board Tab Buttons ====================*/
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Column {
            BoardsPageTabButtons(
                boardViewModel = boardViewModel,
                boardUiState = boardUiState,
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        /** Lists Show ====================*/
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            when (boardUiState.currentSelectedBoardTab) {
                R.string.boardTabTitle -> {
                    ShowListBoard(
                        boardListUiState = boardViewModel.boardListUiState,
                        userUiState = userUiState,
                        planUiState = planUiState,
                        boardViewModel = boardViewModel,
                        boardUiState = boardUiState,
                        onBoardClicked = onBoardClicked,
                        onResetButtonClicked = onResetButtonClicked,
                    )
                }

                R.string.companyTabTitle -> {
//                    val listCompanyRepoViewModel: ListCompanyRepoViewModel = viewModel(factory = ListCompanyRepoViewModel.CompanyFactory)
                    ShowListCompany(
                        companyListUiState = boardViewModel.companyListUiState,
                        userUiState = userUiState,
                        planUiState = planUiState,
                        boardViewModel = boardViewModel,
                        boardUiState = boardUiState,
                        onBoardClicked = onBoardClicked,
                        onResetButtonClicked = onResetButtonClicked,
                    )
                }

                R.string.tradeTabTitle -> {
//                    val listTradeRepoViewModel: ListTradeRepoViewModel = viewModel(factory = ListTradeRepoViewModel.TradeFactory)
                    ShowListTrade(
                        tradeListUiState = boardViewModel.tradeListUiState,
                        userUiState = userUiState,
                        planUiState = planUiState,
                        boardViewModel = boardViewModel,
                        boardUiState = boardUiState,
                        onBoardClicked = onBoardClicked,
                        onResetButtonClicked = onResetButtonClicked,
                    )
                }
            }
        }
    }
}

// HTML 태그 제거 함수 (검색에 사용)
private fun removeHtmlTags(text: String): String {
    return text.replace(Regex("<[^>]*>"), "")
}

// 검색 결과 없을 시
@Composable
fun NoSearchFoundScreen(
) {
    Text(text = stringResource(R.string.noSearchFound))
}

// 게시판에 글이 없을 시
@Composable
fun NoArticlesFoundScreen(
) {
    Text(text = stringResource(R.string.noArticlesFound))
}
