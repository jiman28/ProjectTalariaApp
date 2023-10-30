package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.login.data.UserUiState

@Composable
fun BoardsPageButtons(
    selectedBoard: Int,
    userUiState: UserUiState,
    planUiState: PlanUiState,
    onBoardListClicked: () -> Unit = {},
    onCompanyListClicked: () -> Unit = {},
    onTradeListClicked: () -> Unit = {},
    onReplyListClicked: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Column {
            OutlinedButton(
                modifier = Modifier
                    .padding(1.dp),
                onClick = { onReplyListClicked() },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (selectedBoard == R.string.reply) Color.Yellow else Color.White,
                )
            ) {
                Text(text = "댓글 체크")
            }
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Row {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { onBoardListClicked() },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (selectedBoard == R.string.board) Color.Yellow else Color.White,
                )
            ) {
                Text(text = "리뷰 모음")
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { onCompanyListClicked() },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (selectedBoard == R.string.company) Color.Yellow else Color.White,
                )
            ) {
                Text(text = "동행자 구인")
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { onTradeListClicked() },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (selectedBoard == R.string.trade) Color.Yellow else Color.White,
                )
            ) {
                Text(text = "거래 시스템")
            }
        }
    }
}