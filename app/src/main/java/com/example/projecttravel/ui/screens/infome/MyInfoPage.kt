package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.infome.infoapi.getPeopleLikeMe
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun MyInfoPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    navController: NavHostController,
    onNextButtonClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val userInfoListViewModel: ViewModelListUserInfo = viewModel(factory = ViewModelListUserInfo.UserInfoFactory)
    val userPlanListViewModel: ViewModelListPlan = viewModel(factory = ViewModelListPlan.PlanListFactory)
    val boardListViewModel: ViewModelListBoard = viewModel(factory = ViewModelListBoard.BoardFactory)
    val companyListViewModel: ViewModelListCompany = viewModel(factory = ViewModelListCompany.CompanyFactory)
    val tradeListViewModel: ViewModelListTrade = viewModel(factory = ViewModelListTrade.TradeFactory)

    val userInfoUiState = (userInfoListViewModel.userInfoUiState as? UserInfoUiState.UserInfoSuccess)
    val planListUiState = (userPlanListViewModel.planListUiState as? PlanListUiState.PlanListSuccess)
    val boardUiState = (boardListViewModel.boardUiState as? BoardUiState.BoardSuccess)
    val companyUiState = (companyListViewModel.companyUiState as? CompanyUiState.CompanySuccess)
    val tradeUiState = (tradeListViewModel.tradeUiState as? TradeUiState.TradeSuccess)

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    var peopleErrorMsg by remember { mutableStateOf("") }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> TextMsgErrorDialog(
                txtErrorMsg = peopleErrorMsg,
                onDismiss = { isLoadingState = null })

            else -> isLoadingState = null
        }
    }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
    ) {
        if (userUiState.checkOtherUser != null) {
            val currentUserMenuId = userUiState.checkOtherUser.id
            val currentUserMenuEmail = userUiState.checkOtherUser.email
            /** filtered Lists for User Menus */
            val filteredInfoGraph = userInfoUiState?.userInfoList?.filter { userInfoItem ->
                val idTag = userInfoItem.user
                val boardMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                boardMatchesId
            }
            val filteredPlanList = planListUiState?.planList?.filter { planItem ->
                val emailTag = planItem.email
                val boardMatchesId = emailTag.contains(currentUserMenuEmail, ignoreCase = true)
                boardMatchesId
            }
            val filteredBoardList = boardUiState?.boardList?.filter { boardItem ->
                val idTag = boardItem.userId
                val boardMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                boardMatchesId
            }
            val filteredCompanyList = companyUiState?.companyList?.filter { companyItem ->
                val idTag = companyItem.userId
                val companyMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                companyMatchesId
            }

            val filteredTradeList = tradeUiState?.tradeList?.filter { tradeItem ->
                val idTag = tradeItem.userId
                val tradeMatchesId = idTag.contains(currentUserMenuId, ignoreCase = true)
                tradeMatchesId
            }

            /** User Menus */
            Column {
                /** ================================================== */
                /** UserInfos */
                Column {
                    Button(
                        onClick = {
                            scope.launch {
                                isLoadingState = true
                                val peopleDeferred = async { userUiState.currentLogin?.let { getPeopleLikeMe(it) } }
                                val peopleComplete = peopleDeferred.await()
                                if (peopleComplete != null) {
                                    isLoadingState = null
                                    userViewModel.setLikeUsers(peopleComplete)
                                    onNextButtonClicked()
                                } else {
                                    peopleErrorMsg = "에러터짐"
                                    isLoadingState = false
                                }
                            }
                        },
                    ) {
                        Text("나랑 비슷한 성향 찾기")
                    }
                }
                Column {
                    Text(text = "현재 확인 중인 아이디", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 이름 : ${userUiState.checkOtherUser.name}", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 아이디 : ${userUiState.checkOtherUser.id}", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 이메일 : ${userUiState.checkOtherUser.email}", modifier = Modifier.padding(5.dp))
                    if (userUiState.checkOtherUser.picture != null) Text(text = "현재 사진 : ${userUiState.checkOtherUser.picture}")
                    Text(text = "현재 게시글 개수 : ${allBoardsCount(filteredBoardList,filteredCompanyList,filteredTradeList)}", modifier = Modifier.padding(5.dp))
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
                            UserPlanList(
                                filteredPlanList = filteredPlanList,
                                contentPadding = PaddingValues(0.dp),
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

fun allBoardsCount(
    filteredBoardList: List<Board>?,
    filteredCompanyList: List<Company>?,
    filteredTradeList: List<Trade>?
): Int {
    val boardCount = filteredBoardList?.size ?: 0
    val companyCount = filteredCompanyList?.size ?: 0
    val tradeCount = filteredTradeList?.size ?: 0

    return boardCount + companyCount + tradeCount
}