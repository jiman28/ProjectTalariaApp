package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.user.PlanListUiState
import com.example.projecttravel.ui.screens.viewmodels.user.ViewModelListPlan
import com.example.projecttravel.ui.screens.viewmodels.user.ViewModelListUserInfo

@Composable
fun MyInfoPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    navController: NavHostController,
) {
    val scope = rememberCoroutineScope()
    val userInfoListViewModel: ViewModelListUserInfo = viewModel(factory = ViewModelListUserInfo.UserInfoFactory)
    val userPlanListViewModel: ViewModelListPlan = viewModel(factory = ViewModelListPlan.PlanListFactory)

    Column (
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
    ) {
        Column {
            if (userUiState.checkOtherUser != null) {
                Column {

                    Text(text = "현재 확인 중인 아이디", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 이름${userUiState.checkOtherUser.name}", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 아이디${userUiState.checkOtherUser.id}", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 이메일${userUiState.checkOtherUser.email}", modifier = Modifier.padding(5.dp))
                    if (userUiState.checkOtherUser.picture != null) Text(text = userUiState.checkOtherUser.picture)
                }
            }

            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

            /** ================================================== */
            /** UserInfos */

            Column {
                MyInfoPageTabButtons(
                    userUiState = userUiState,
                    userViewModel = userViewModel
                )
            }

            Column {
                when (userUiState.currentSelectedUserTab) {
                    R.string.userTabMenuBoard -> {
                        UserWrittenBoard(
                            userUiState = userUiState,
                            planUiState = planUiState,
                            boardSelectUiState = boardSelectUiState,
                            boardSelectViewModel = boardSelectViewModel,
                            onBoardClicked = {
                                navController.navigate(TravelScreen.Page4A.name) {
//                                    popUpTo(TravelScreen.Page4.name) {
//                                        inclusive = false // Page4A는 제외
//                                    }
//                                    launchSingleTop = true    // 혹시 모르니 일단 dump 로 냅두자
                                }
                                             },
                        )
                    }

                    R.string.userTabMenuPlans -> {
                        val planListUiState =
                            (userPlanListViewModel.planListUiState as? PlanListUiState.PlanListSuccess)
                        if (planListUiState != null) {
                            UserPlanList(
                                planListUiState = planListUiState,
                                contentPadding = PaddingValues(0.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
