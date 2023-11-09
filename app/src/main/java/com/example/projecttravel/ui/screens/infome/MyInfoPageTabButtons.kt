package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.viewmodels.UserViewModel

@Composable
fun MyInfoPageTabButtons(
    userUiState: UserUiState,
    userViewModel: UserViewModel,
) {
    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Row {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { userViewModel.setUserPageTab(R.string.userTabMenuBoard) },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (userUiState.currentSelectedUserTab == R.string.userTabMenuBoard) Color.Yellow else Color.White,
                )
            ) {
                Text(text = stringResource(R.string.userTabMenuBoard))
            }
            OutlinedButton(
                modifier = Modifier
                    .weight(1f).padding(1.dp),
                onClick = { userViewModel.setUserPageTab(R.string.userTabMenuPlans) },
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    if (userUiState.currentSelectedUserTab == R.string.userTabMenuPlans) Color.Yellow else Color.White,
                )
            ) {
                Text(text = stringResource(R.string.userTabMenuPlans))
            }
        }
    }
}
