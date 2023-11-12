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
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.Trade
import com.example.projecttravel.ui.screens.GlobalErrorScreen
import com.example.projecttravel.ui.screens.GlobalLoadingScreen
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.uistates.viewmodels.PlanViewModel
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
//import com.example.projecttravel.data.repositories.board.viewmodels.BoardUiState
//import com.example.projecttravel.data.repositories.board.viewmodels.CompanyUiState
//import com.example.projecttravel.data.repositories.board.viewmodels.TradeUiState
//import com.example.projecttravel.data.repositories.board.viewmodels.ListBoardRepoViewModel
import com.example.projecttravel.data.repositories.user.viewmodels.PlanListUiState
import com.example.projecttravel.data.repositories.user.viewmodels.UserInfoUiState
import com.example.projecttravel.data.repositories.user.viewmodels.ListUserPlansRepoViewModel
import com.example.projecttravel.data.repositories.user.viewmodels.ListUserInfoRepoViewModel
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.TravelScreen

@Composable
fun MyInfoPage(
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    planUiState: PlanUiState,
    planViewModel: PlanViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    navController: NavHostController,
    onBoardClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onResetButtonClicked: () -> Unit,
) {
//    val listUserInfoRepoViewModel: ListUserInfoRepoViewModel = viewModel(factory = ListUserInfoRepoViewModel.UserInfoFactory)
//    val listUserPlansRepoViewModel: ListUserPlansRepoViewModel = viewModel(factory = ListUserPlansRepoViewModel.PlanListFactory)
//    val listBoardRepoViewModel: ListBoardRepoViewModel = viewModel(factory = ListBoardRepoViewModel.BoardFactory)
//    val listCompanyRepoViewModel: ListCompanyRepoViewModel = viewModel(factory = ListCompanyRepoViewModel.CompanyFactory)
//    val listTradeRepoViewModel: ListTradeRepoViewModel = viewModel(factory = ListTradeRepoViewModel.TradeFactory)

//    val userInfoUiState = (listUserInfoRepoViewModel.userInfoUiState as? UserInfoUiState.UserInfoSuccess)
//    val planListUiState = (userPlanListViewModel.planListUiState as? PlanListUiState.PlanListSuccess)
//    val boardUiState = (listBoardRepoViewModel.boardUiState as? BoardUiState.BoardSuccess)
//    val companyUiState = (listCompanyRepoViewModel.companyUiState as? CompanyUiState.CompanySuccess)
//    val tradeUiState = (listTradeRepoViewModel.tradeUiState as? TradeUiState.TradeSuccess)

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
//            val filteredInfoGraph = userInfoUiState?.userInterestList?.firstOrNull { userInfoItem ->
//                val idTag = userInfoItem.user
//                val boardMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
//                boardMatchesId
//            }
//            val filteredBoardList = boardUiState?.boardList?.filter { boardItem ->
//                val idTag = boardItem.userId
//                val boardMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
//                boardMatchesId
//            }
//            val filteredCompanyList = companyUiState?.companyList?.filter { companyItem ->
//                val idTag = companyItem.userId
//                val companyMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
//                companyMatchesId
//            }
//
//            val filteredTradeList = tradeUiState?.tradeList?.filter { tradeItem ->
//                val idTag = tradeItem.userId
//                val tradeMatchesId = idTag.equals(currentUserMenuId, ignoreCase = true)
//                tradeMatchesId
//            }

//            val filteredAllBoardCount = countAllBoardsOfUser(filteredBoardList,filteredCompanyList,filteredTradeList)

            /** User Menus */
            Column (

            ) {
                Spacer(modifier = Modifier.padding(5.dp))
                /** ================================================== */
                /** UserInfos */
                Row {
                    Column {
                        UserProfiles(
                            currentUserInfo = userUiState.checkOtherUser,
                            userUiState = userUiState,
                            userViewModel = userViewModel,
                            boardPageUiState = boardPageUiState,
                            boardPageViewModel = boardPageViewModel,
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
                                boardPageUiState = boardPageUiState,
                                boardPageViewModel = boardPageViewModel,
                                onResetButtonClicked = onResetButtonClicked,
                                onBoardClicked = onBoardClicked,
                            )
                        }

                        R.string.userTabMenuPlans -> {
                            UserPlanList(
                                userUiState = userUiState,
                                userViewModel = userViewModel,
                                planViewModel = planViewModel,
                                navController = navController,
                                onNextButtonClicked = onNextButtonClicked,
                            )
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
