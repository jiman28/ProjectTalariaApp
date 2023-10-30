package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.login.data.UserUiState

@Composable
fun AllBoardsPage(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    onBackButtonClicked: () -> Unit = {},
) {
    var selectedBoard by remember { mutableIntStateOf(R.string.board) }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        /** Buttons ====================*/
        Column {
            BoardsPageButtons(
                selectedBoard = selectedBoard,
                userUiState = userUiState,
                planUiState = planUiState,
                onBoardListClicked = { selectedBoard = R.string.board },
                onCompanyListClicked = { selectedBoard = R.string.company },
                onTradeListClicked = { selectedBoard = R.string.trade },
            ) { selectedBoard = R.string.reply }
        }
        Column {
            val title = when (selectedBoard) {
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
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        ) {
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(modifier = Modifier.weight(1f), textAlign = TextAlign.Center, text = "글번호")
                Divider(color = Color.Black, modifier = Modifier.height(22.dp).width(1.dp))
                Text(modifier = Modifier.weight(7f), textAlign = TextAlign.Center, text = "제목")
            }
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        }

        /** Lists Show ====================*/
        Column {
            when (selectedBoard) {
                R.string.board -> {
                    ListBoard(
                        selectedBoard = selectedBoard,
                        contentPadding = PaddingValues(0.dp),
                    )
                }

                R.string.company -> {
                    ListCompany(
                        contentPadding = PaddingValues(0.dp),
                    )
                }

                R.string.trade -> {
                    ListTrade(
                        contentPadding = PaddingValues(0.dp),
                    )
                }

                else -> {
                    ListCheckReply(
                        contentPadding = PaddingValues(0.dp),
                    )
                }
            }
        }
    }
}
