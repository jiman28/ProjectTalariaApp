package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.Trade
import com.example.projecttravel.ui.screens.GlobalErrorScreen
import com.example.projecttravel.ui.screens.GlobalLoadingScreen
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.board.BoardUiState
import com.example.projecttravel.ui.screens.viewmodels.board.CompanyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.TradeUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListBoard
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListCompany
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListTrade
import com.example.projecttravel.ui.screens.viewmodels.user.PlanListUiState
import com.example.projecttravel.ui.screens.viewmodels.user.UserInfoUiState
import com.example.projecttravel.ui.screens.viewmodels.user.ViewModelListPlan
import com.example.projecttravel.ui.screens.viewmodels.user.ViewModelListUserInfo

@Composable
fun MyInfoPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    navController: NavHostController,
    onNextButtonClicked: () -> Unit,
) {
    val userInfoListViewModel: ViewModelListUserInfo = viewModel(factory = ViewModelListUserInfo.UserInfoFactory)
    val userPlanListViewModel: ViewModelListPlan = viewModel(factory = ViewModelListPlan.PlanListFactory)
    val boardListViewModel: ViewModelListBoard = viewModel(factory = ViewModelListBoard.BoardFactory)
    val companyListViewModel: ViewModelListCompany = viewModel(factory = ViewModelListCompany.CompanyFactory)
    val tradeListViewModel: ViewModelListTrade = viewModel(factory = ViewModelListTrade.TradeFactory)

    val userInfoUiState = (userInfoListViewModel.userInfoUiState as? UserInfoUiState.UserInfoSuccess)
//    val planListUiState = (userPlanListViewModel.planListUiState as? PlanListUiState.PlanListSuccess)
    val boardUiState = (boardListViewModel.boardUiState as? BoardUiState.BoardSuccess)
    val companyUiState = (companyListViewModel.companyUiState as? CompanyUiState.CompanySuccess)
    val tradeUiState = (tradeListViewModel.tradeUiState as? TradeUiState.TradeSuccess)

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
    ) {
        if (userUiState.checkOtherUser != null) {
            val currentUserMenuId = userUiState.checkOtherUser.id
            val currentUserMenuEmail = userUiState.checkOtherUser.email
            val currentUserMenuName = userUiState.checkOtherUser.name
            val currentUserMenuPicture = userUiState.checkOtherUser.picture
            /** filtered Lists for User Menus (perfectly matching => equals) */
            val filteredInfoGraph = userInfoUiState?.userInfoList?.firstOrNull { userInfoItem ->
                val idTag = userInfoItem.user
                val boardMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
                boardMatchesId
            }
            val filteredBoardList = boardUiState?.boardList?.filter { boardItem ->
                val idTag = boardItem.userId
                val boardMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
                boardMatchesId
            }
            val filteredCompanyList = companyUiState?.companyList?.filter { companyItem ->
                val idTag = companyItem.userId
                val companyMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
                companyMatchesId
            }

            val filteredTradeList = tradeUiState?.tradeList?.filter { tradeItem ->
                val idTag = tradeItem.userId
                val tradeMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
                tradeMatchesId
            }

            val filteredAllBoardCount = countAllBoardsOfUser(filteredBoardList,filteredCompanyList,filteredTradeList)

            /** User Menus */
            Column (

            ) {
                Spacer(modifier = Modifier.padding(5.dp))
                /** ================================================== */
                /** UserInfos */
                Row {
                    Column {
                        UserProfiles(
                            filteredInfoGraph = filteredInfoGraph,
                            currentUserInfo = userUiState.checkOtherUser,
                            allBoardsCounts = filteredAllBoardCount,
                            userUiState = userUiState,
                            userViewModel = userViewModel,
                            navController = navController,
                        )
                    }

                    Spacer(modifier = Modifier.padding(5.dp))
                    Column {

                    }
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                /** ================================================== */
                /** User Menus Tab Buttons*/
                Column {
                    MyInfoPageTabButtons(
                        userUiState = userUiState,
                        userViewModel = userViewModel
                    )
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                /** ================================================== */
                /** User Menus Tab Menus*/
                Column {
                    when (userUiState.currentSelectedUserTab) {
                        R.string.userTabMenuBoard -> {
                            UserWrittenBoard(
                                userUiState = userUiState,
                                planUiState = planUiState,
                                boardSelectUiState = boardSelectUiState,
                                filteredBoardList = filteredBoardList,
                                filteredCompanyList = filteredCompanyList,
                                filteredTradeList = filteredTradeList,
                                boardSelectViewModel = boardSelectViewModel,
                                onBoardClicked = { navController.navigate(TravelScreen.Page4A.name) },
                            )
                        }

                        R.string.userTabMenuPlans -> {
                            when (userPlanListViewModel.planListUiState) {
                                is PlanListUiState.Loading -> GlobalLoadingScreen()
                                is PlanListUiState.PlanListSuccess -> {
                                    val planListUiState = (userPlanListViewModel.planListUiState as PlanListUiState.PlanListSuccess)
                                    val filteredPlanList = planListUiState.planList.filter { planItem ->
                                        val emailTag = planItem.email
                                        val boardMatchesId = emailTag.equals(currentUserMenuEmail, ignoreCase = true)
                                        boardMatchesId
                                    }
                                    UserPlanList(
                                        userUiState = userUiState,
                                        userViewModel = userViewModel,
                                        planViewModel = planViewModel,
                                        navController = navController,
                                        filteredPlanList = filteredPlanList,
                                        onNextButtonClicked = onNextButtonClicked,
                                    )
                                }
                                else -> GlobalErrorScreen(userPlanListViewModel::getPlanList)
                            }
                        }
                    }
                }
            }
            /** ================================================== */
        } else {
            Text(text = "아이디 확인 과정에서 오류 발생\n다시 시도하길 바람", modifier = Modifier.padding(5.dp))
        }
    }
}

fun countAllBoardsOfUser(
    filteredBoardList: List<Board>?,
    filteredCompanyList: List<Company>?,
    filteredTradeList: List<Trade>?
): Int {
    val boardCount = filteredBoardList?.size ?: 0
    val companyCount = filteredCompanyList?.size ?: 0
    val tradeCount = filteredTradeList?.size ?: 0

    return boardCount + companyCount + tradeCount
}
