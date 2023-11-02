package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        /** Board Search and Write Buttons ====================*/
        Row {
            Button(
                modifier = Modifier
                    .padding(1.dp),
                onClick = { onWriteButtonClicked() },
                shape = RoundedCornerShape(0.dp),
            ) {
                Text(text = "게시글 작성")
            }
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
            when (boardSelectUiState.currentSelectedBoard) {
                R.string.board -> {
                    if (boardUiState != null) {
                        ListBoard(
                            boardUiState = boardUiState,
                            boardSelectViewModel = boardSelectViewModel,
                            onBoardClicked = onBoardClicked,
                            contentPadding = PaddingValues(0.dp),
                        )
                    } else {
                        Text(text = "아직 아무도 글 안 썻다우")
                    }
                }

                R.string.company -> {
                    if (companyUiState != null) {
                        ListCompany(
                            companyUiState = companyUiState,
                            boardSelectViewModel = boardSelectViewModel,
                            onBoardClicked = onBoardClicked,
                            contentPadding = PaddingValues(0.dp),
                        )
                    } else {
                        Text(text = "아직 아무도 글 안 썻다우")
                    }
                }

                R.string.trade -> {
                    if (tradeUiState != null) {
                        ListTrade(
                            tradeUiState = tradeUiState,
                            boardSelectViewModel = boardSelectViewModel,
                            onBoardClicked = onBoardClicked,
                            contentPadding = PaddingValues(0.dp),
                        )
                    } else {
                        Text(text = "아직 아무도 글 안 썻다우")
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
