package com.example.projecttravel.ui.screens.infomeedit

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.uistates.viewmodels.PlanViewModel
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import com.example.projecttravel.model.SendSignIn
import com.example.projecttravel.ui.screens.auth.SignCredentials
import com.example.projecttravel.ui.screens.auth.api.signInApiCall
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun EditUserPage(
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    planUiState: PlanUiState,
    planViewModel: PlanViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    navController: NavHostController,
) {

    var editStatus by remember { mutableStateOf<String?>(null) }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
    ) {
        if (editStatus == "edit") {
            UserInfoEditForm(
                userUiState = userUiState,
                userViewModel = userViewModel,
                planUiState = planUiState,
                planViewModel = planViewModel,
                boardPageUiState = boardPageUiState,
                boardPageViewModel = boardPageViewModel,
                navController = navController,
            )

        } else if(editStatus == "withdrawal") {
            UserWithdrawalForm(
                userUiState = userUiState,
                userViewModel = userViewModel,
                planUiState = planUiState,
                planViewModel = planViewModel,
                boardPageUiState = boardPageUiState,
                boardPageViewModel = boardPageViewModel,
                navController = navController,
            )
        } else {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 50.dp)
            ){
                Button(
                    onClick = {
                        editStatus = "edit"
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("회원 정보 수정")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        editStatus = "withdrawal"
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("회원 탈퇴")
                }

            }
        }
    }
}