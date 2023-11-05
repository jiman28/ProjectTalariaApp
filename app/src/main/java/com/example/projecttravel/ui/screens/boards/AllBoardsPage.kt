package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.board.BoardUiState
import com.example.projecttravel.ui.screens.viewmodels.board.CompanyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.TradeUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListBoard
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListCompany
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListTrade

@Composable
fun AllBoardsPage(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onBoardClicked: () -> Unit,
    onWriteButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    val boardListViewModel: ViewModelListBoard = viewModel(factory = ViewModelListBoard.BoardFactory)
    val companyListViewModel: ViewModelListCompany = viewModel(factory = ViewModelListCompany.CompanyFactory)
    val tradeListViewModel: ViewModelListTrade = viewModel(factory = ViewModelListTrade.TradeFactory)

    val boardUiState = (boardListViewModel.boardUiState as? BoardUiState.BoardSuccess)
    val companyUiState = (companyListViewModel.companyUiState as? CompanyUiState.CompanySuccess)
    val tradeUiState = (tradeListViewModel.tradeUiState as? TradeUiState.TradeSuccess)

    var searchKeyWord by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        /** Board Search and Write Buttons ====================*/
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Button(
                modifier = Modifier
                    .padding(1.dp),
                onClick = { onWriteButtonClicked() },
                shape = RoundedCornerShape(0.dp),
            ) {
                Text(text = "게시글 작성")
            }

            val scrollStateText = rememberScrollState()
            TextField(
                value = searchKeyWord,
                onValueChange = { newValue -> searchKeyWord = newValue },
                label = { Text(text = "제목이나 내용 검색") },
                maxLines = 1,
                modifier = Modifier
                    .padding(16.dp)
                    .horizontalScroll(scrollStateText)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done // 완료 버튼 활성화
                ),
            )
        }
        /** Board Tab Buttons ====================*/
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Column {
            BoardsPageTabButtons(
                boardSelectUiState = boardSelectUiState,
                boardSelectViewModel = boardSelectViewModel,
                userUiState = userUiState,
                planUiState = planUiState,
            )
        }
        Column {
            val title = when (boardSelectUiState.currentSelectedBoard) {
                R.string.board -> "여행 후기를 자유롭게 쓰세요!"
                R.string.company -> "같이 여행 갈 사람 구합니다!"
                R.string.trade -> "비행기 티켓 팔고 싶어요!"
                else -> "몰루"
            }
            Text(
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                text = title
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        ) {
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(modifier = Modifier.weight(1f), textAlign = TextAlign.Center, text = "글번호")
                Text(modifier = Modifier.weight(7f), textAlign = TextAlign.Center, text = "제목")
            }
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        }
        Spacer(modifier = Modifier.padding(2.dp))
        /** Lists Show ====================*/
        Column {
            // 검색 했을때
            if (searchKeyWord != "") {
                val filteredBoardList = boardUiState?.boardList?.filter { boardItem ->
                    // 여기서 boardItem은 게시글 목록의 각 항목입니다.
                    // 검색어(searchKeyWord)와 게시글 제목 또는 내용에 대한 일치 여부를 확인합니다.
                    val titleWithoutTags = removeHtmlTags(boardItem.title)
                    val contentWithoutTags = removeHtmlTags(boardItem.content)

                    val titleContainsKeyword = titleWithoutTags.contains(searchKeyWord, ignoreCase = true)
                    val contentContainsKeyword = contentWithoutTags.contains(searchKeyWord, ignoreCase = true)
                    // 검색어가 제목, 내용 중 하나라도 일치하면 true를 반환합니다.
                    titleContainsKeyword || contentContainsKeyword
                }

                val filteredCompanyList = companyUiState?.companyList?.filter { companyItem ->
                    // 여기서 companyItem은 게시글 목록의 각 항목입니다.
                    // 검색어(searchKeyWord)와 게시글 제목 또는 내용에 대한 일치 여부를 확인합니다.
                    val titleWithoutTags = removeHtmlTags(companyItem.title)
                    val contentWithoutTags = removeHtmlTags(companyItem.content)

                    val titleContainsKeyword = titleWithoutTags.contains(searchKeyWord, ignoreCase = true)
                    val contentContainsKeyword = contentWithoutTags.contains(searchKeyWord, ignoreCase = true)
                    // 검색어가 제목, 내용 중 하나라도 일치하면 true를 반환합니다.
                    titleContainsKeyword || contentContainsKeyword
                }

                val filteredTradeList = tradeUiState?.tradeList?.filter { tradeItem ->
                    // 여기서 tradeItem은 게시글 목록의 각 항목입니다.
                    // 검색어(searchKeyWord)와 게시글 제목 또는 내용에 대한 일치 여부를 확인합니다.
                    val titleWithoutTags = removeHtmlTags(tradeItem.title)
                    val contentWithoutTags = removeHtmlTags(tradeItem.content)

                    val titleContainsKeyword = titleWithoutTags.contains(searchKeyWord, ignoreCase = true)
                    val contentContainsKeyword = contentWithoutTags.contains(searchKeyWord, ignoreCase = true)
                    // 검색어가 제목, 내용 중 하나라도 일치하면 true를 반환합니다.
                    titleContainsKeyword || contentContainsKeyword
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
                                    NoSearchFoundScreen()
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
                                    NoSearchFoundScreen()
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
                                    NoSearchFoundScreen()
                                }
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }
                    else -> {
                        CheckReply(
                            contentPadding = PaddingValues(0.dp),
                        )
                    }
                }

            // 검색 안했을때
            } else {
                when (boardSelectUiState.currentSelectedBoard) {
                    R.string.board -> {
                        if (boardUiState != null) {
                            if (boardUiState.boardList.isNotEmpty()) {
                                ListBoard(
                                    boardUiState = boardUiState,
                                    boardSelectViewModel = boardSelectViewModel,
                                    onBoardClicked = onBoardClicked,
                                    contentPadding = PaddingValues(0.dp),
                                )
                            }
                            else {
                                NoArticlesFoundScreen()
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }

                    R.string.company -> {
                        if (companyUiState != null) {
                            if (companyUiState.companyList.isNotEmpty()) {
                                ListCompany(
                                    companyUiState = companyUiState,
                                    boardSelectViewModel = boardSelectViewModel,
                                    onBoardClicked = onBoardClicked,
                                    contentPadding = PaddingValues(0.dp),
                                )
                            }
                            else {
                                NoArticlesFoundScreen()
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }

                    R.string.trade -> {
                        if (tradeUiState != null) {
                            if (tradeUiState.tradeList.isNotEmpty()) {
                                ListTrade(
                                    tradeUiState = tradeUiState,
                                    boardSelectViewModel = boardSelectViewModel,
                                    onBoardClicked = onBoardClicked,
                                    contentPadding = PaddingValues(0.dp),
                                )
                            }
                            else {
                                NoArticlesFoundScreen()
                            }
                        } else {
                            NoArticlesFoundScreen()
                        }
                    }
                    else -> {
                        CheckReply(
                            contentPadding = PaddingValues(0.dp),
                        )
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
){
    Text(text = stringResource(R.string.noSearchFound))
}

// 게시판에 글이 없을 시
@Composable
fun NoArticlesFoundScreen(
){
    Text(text = stringResource(R.string.noArticlesFound))
}
