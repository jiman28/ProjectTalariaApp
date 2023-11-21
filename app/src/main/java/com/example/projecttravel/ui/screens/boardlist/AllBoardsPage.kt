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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.repositories.board.viewmodels.ListBoardRepoViewModel
import com.example.projecttravel.data.repositories.board.viewmodels.ListCompanyRepoViewModel
import com.example.projecttravel.data.repositories.board.viewmodels.ListTradeRepoViewModel
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
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
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
                boardPageUiState = boardPageUiState,
                boardPageViewModel = boardPageViewModel,
                onWriteButtonClicked = onWriteButtonClicked,
                onResetButtonClicked = onResetButtonClicked,
                )
        }

        /** Board Tab Buttons ====================*/
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Column {
            BoardsPageTabButtons(
                boardPageUiState = boardPageUiState,
                boardPageViewModel = boardPageViewModel,
            )
        }
//        Column {
//            val title = when (boardPageUiState.currentSelectedBoardTab) {
//                R.string.boardTabTitle -> "여행 후기를 자유롭게 쓰세요!"
//                R.string.companyTabTitle -> "같이 여행 갈 사람 구합니다!"
//                R.string.tradeTabTitle -> "비행기 티켓 팔고 싶어요!"
//                else -> "몰루"
//            }
//            Text(
//                modifier = Modifier.padding(2.dp),
//                fontWeight = FontWeight.ExtraBold,
//                fontStyle = FontStyle.Italic,
//                fontSize = 25.sp,
//                textAlign = TextAlign.Center,
//                text = title
//            )
//        }
        Spacer(modifier = Modifier.padding(2.dp))
//        Column(
//            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
//            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
//            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
//        ) {
//            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center,
//            ) {
//                Text(modifier = Modifier.weight(1f), textAlign = TextAlign.Center, text = "글번호")
//                Text(modifier = Modifier.weight(7f), textAlign = TextAlign.Center, text = "제목")
//            }
//            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//        }
//        Spacer(modifier = Modifier.padding(2.dp))
        /** Lists Show ====================*/
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            when (boardPageUiState.currentSelectedBoardTab) {
                R.string.boardTabTitle -> {
//                    val listBoardRepoViewModel: ListBoardRepoViewModel = viewModel(factory = ListBoardRepoViewModel.BoardFactory)
                    if (boardPageUiState.currentBoardList != null && boardPageUiState.currentBoardList.list.isNotEmpty()) {
                        ListBoardEntity(
                            boardList = boardPageUiState.currentBoardList,
                            userUiState = userUiState,
                            planUiState = planUiState,
                            boardPageUiState = boardPageUiState,
                            boardPageViewModel = boardPageViewModel,
                            onBoardClicked = onBoardClicked,
                            onResetButtonClicked = onResetButtonClicked,
                        )
                    } else {
                        NoArticlesFoundScreen()
                    }

                }

                R.string.companyTabTitle -> {
//                    val listCompanyRepoViewModel: ListCompanyRepoViewModel = viewModel(factory = ListCompanyRepoViewModel.CompanyFactory)
                    if (boardPageUiState.currentCompanyList != null && boardPageUiState.currentCompanyList.list.isNotEmpty()) {
                        ListCompanyEntity(
                            companyList = boardPageUiState.currentCompanyList,
                            userUiState = userUiState,
                            planUiState = planUiState,
                            boardPageUiState = boardPageUiState,
                            boardPageViewModel = boardPageViewModel,
                            onBoardClicked = onBoardClicked,
                            onResetButtonClicked = onResetButtonClicked,
                        )
                    } else {
                        NoArticlesFoundScreen()
                    }
                }

                R.string.tradeTabTitle -> {
//                    val listTradeRepoViewModel: ListTradeRepoViewModel = viewModel(factory = ListTradeRepoViewModel.TradeFactory)
                    if (boardPageUiState.currentTradeList != null && boardPageUiState.currentTradeList.list.isNotEmpty()) {
                        ListTradeEntity(
                            tradeList = boardPageUiState.currentTradeList,
                            userUiState = userUiState,
                            planUiState = planUiState,
                            boardPageUiState = boardPageUiState,
                            boardPageViewModel = boardPageViewModel,
                            onBoardClicked = onBoardClicked,
                            onResetButtonClicked = onResetButtonClicked,
                        )
                    } else {
                        NoArticlesFoundScreen()
                    }
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
