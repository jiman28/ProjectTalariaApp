package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.login.data.UserUiState
import com.example.projecttravel.ui.screens.selection.selectdialogs.ResetConfirmDialog

@Composable
fun BoardsPageButtons(
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
                    .padding(3.dp),
                onClick = { onReplyListClicked() }
            ) {
                Text(text = "댓글 체크")
            }
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Row {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = { onBoardListClicked() }
            ) {
                Text(text = "자유 게시판")
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = { onCompanyListClicked() }
            ) {
                Text(text = "동행자 구인")
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = { onTradeListClicked() }
            ) {
                Text(text = "중고 거래소")
            }
        }
    }
}
