package com.example.projecttravel.zzztester

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.projecttravel.ui.viewmodels.BoardViewModel
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserPageUiState
import com.example.projecttravel.ui.viewmodels.PlanPageViewModel
import com.example.projecttravel.ui.viewmodels.UserPageViewModel
import com.example.projecttravel.ui.viewmodels.BoardUiState
import kotlinx.coroutines.CoroutineScope

@Composable
fun TestPage(
    userPageUiState: UserPageUiState,
    userPageViewModel: UserPageViewModel,
    planUiState: PlanUiState,
    planPageViewModel: PlanPageViewModel,
    boardViewModel: BoardViewModel,
    boardUiState: BoardUiState,
    navController: NavHostController,
    scope: CoroutineScope,
) {

}


