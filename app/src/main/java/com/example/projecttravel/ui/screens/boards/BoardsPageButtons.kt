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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.login.data.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect

@Composable
fun BoardsPageButtons(
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    userUiState: UserUiState,
    planUiState: PlanUiState,
) {
    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Column {
            OutlinedButton(
                modifier = Modifier
                    .padding(1.dp),
                onClick = { boardSelectViewModel.setCurrent(R.string.reply) },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (boardSelectUiState.currentSelectedBoard == R.string.reply) Color.Yellow else Color.White,
                )
            ) {
                Text(text = stringResource(R.string.replyTitle))
            }
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Row {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { boardSelectViewModel.setCurrent(R.string.board) },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (boardSelectUiState.currentSelectedBoard == R.string.board) Color.Yellow else Color.White,
                )
            ) {
                Text(text = stringResource(R.string.boardTitle))
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { boardSelectViewModel.setCurrent(R.string.company) },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (boardSelectUiState.currentSelectedBoard == R.string.company) Color.Yellow else Color.White,
                )
            ) {
                Text(text = stringResource(R.string.companyTitle))
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { boardSelectViewModel.setCurrent(R.string.trade) },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (boardSelectUiState.currentSelectedBoard == R.string.trade) Color.Yellow else Color.White,
                )
            ) {
                Text(text = stringResource(R.string.tradeTitle))
            }
        }
    }
}
