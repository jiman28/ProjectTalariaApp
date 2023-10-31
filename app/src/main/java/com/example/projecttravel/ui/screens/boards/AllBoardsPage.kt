package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.login.data.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect

@Composable
fun AllBoardsPage(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onBoardClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        /** Buttons ====================*/
        Column {
            BoardsPageButtons(
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
                    ListBoard(
                        boardSelectUiState = boardSelectUiState,
                        boardSelectViewModel = boardSelectViewModel,
                        onBoardClicked = onBoardClicked,
                        contentPadding = PaddingValues(0.dp),
                    )
                }
                R.string.company -> {
                    ListCompany(
                        boardSelectUiState = boardSelectUiState,
                        boardSelectViewModel = boardSelectViewModel,
                        onBoardClicked = onBoardClicked,
                        contentPadding = PaddingValues(0.dp),
                    )
                }
                R.string.trade -> {
                    ListTrade(
                        boardSelectUiState = boardSelectUiState,
                        boardSelectViewModel = boardSelectViewModel,
                        onBoardClicked = onBoardClicked,
                        contentPadding = PaddingValues(0.dp),
                    )
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
