package com.example.projecttravel.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.viewmodels.PlanViewModel
import com.example.projecttravel.data.viewmodels.UserViewModel
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.UserResponse
import com.example.projecttravel.ui.screens.auth.datastore.DataStore
import com.example.projecttravel.ui.screens.auth.datastore.DataStore.Companion.dataStore
import com.example.projecttravel.ui.screens.boardlist.readapi.getAllBoardDefault
import com.example.projecttravel.ui.screens.boardlist.readapi.getBoardListMobile
import com.example.projecttravel.ui.screens.infome.infoapi.callMyPlanList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ModalNavigationDrawer Contents
 * */
@Composable
fun DrawerContents (
    onLogOutClicked: () -> Unit,
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    planViewModel: PlanViewModel,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    var isLogOutState by remember { mutableStateOf(false) }
    Surface {
        if (isLogOutState) {
            LogOutDialog(
                userViewModel = userViewModel,
                onDismissAlert = { isLogOutState = false },
                onLogOutClicked = onLogOutClicked
            )
        }
    }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

    ModalDrawerSheet(
        modifier = Modifier.size(200.dp, 500.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
            modifier = Modifier.selectable(
                selected = false,
                onClick = {
                    userViewModel.previousScreenWasPageOneA(true)
                    userViewModel.setUserPageInfo(userUiState.currentLogin)
                    navController.navigate(TravelScreen.Page1A.name)
                    scope.launch { drawerState.close() }
                }
            )
        ) {
            Image(
                painter = painterResource(R.drawable.talaria),
                contentDescription = "UserImage",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "${userUiState.currentLogin?.name} 님, 환영합니다",
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth()
            )

        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))

        TextButton(onClick = {
            userViewModel.previousScreenWasPageOneA(true)
            userViewModel.setUserPageInfo(userUiState.currentLogin)
            navController.navigate(TravelScreen.Page1A.name)
            scope.launch { drawerState.close() }
        }) {
            Text(text = "마이 페이지", fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        TextButton(onClick = {
            isLogOutState = true
            scope.launch {
                drawerState.close()
            }
        }) {
            Text(text = "로그아웃", fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        TextButton(onClick = {
            userViewModel.previousScreenWasPageOneA(false)
            navController.navigate(TravelScreen.Page2.name)
            scope.launch { drawerState.close() }
        }) {
            Text(text = "여행 계획하기", fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        TextButton(onClick = {
            scope.launch {
                drawerState.close()
                isLoadingState = true
                val callBoard = CallBoard(kw = "",page = 0, type = "", email = "")
                val isDeferred = async { getAllBoardDefault(callBoard,boardPageViewModel,scope) }
                val isComplete = isDeferred.await()
                // 모든 작업이 완료되었을 때만 실행합니다.
                if (isComplete) {
                    isLoadingState = null
                    boardPageViewModel.setCurrentSearchKeyWord("")
                    userViewModel.previousScreenWasPageOneA(false)
                    navController.navigate(TravelScreen.Page4.name)
                } else {
                    isLoadingState = false
                }
            }
//            userViewModel.previousScreenWasPageOneA(false)
//            navController.navigate(TravelScreen.Page4.name)
//            scope.launch { drawerState.close() }
        }) {
            Text(text = "게시판", fontSize = 25.sp)
        }

        //TestTestTestTestTestTestTestTest

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        TextButton(onClick = {
            scope.launch {
                drawerState.close()
                isLoadingState = true
                val callBoard = CallBoard(kw = "",page = 0,type = "", email = "b@b.b")
                val userResponse = UserResponse(id = "",email = "",name = "",picture = "")
                val isDeferred = async { getAllBoardDefault(callBoard,boardPageViewModel,scope) }
//                val isDeferred = async { userUiState.currentLogin?.let { callMyPlanList(it) } }
//                val isDeferred = async { getBoardListMobile(callBoard) }
                val isComplete = isDeferred.await()
                // 모든 작업이 완료되었을 때만 실행합니다.
                if (isComplete) {
//                    boardPageViewModel.setBoardList(isComplete)
//                    userViewModel.setCheckUserPlanList(isComplete)
                    isLoadingState = null
                    navController.navigate(TravelScreen.PageTest.name)
                } else {
                    isLoadingState = false
                }
            }
        }) {
            Text(text = "테스트테스트", fontSize = 25.sp)
        }

        //TestTestTestTestTestTestTestTest

    }
}

/** ===================================================================== */
/** LogOutDialog to ask whether to logout or not ====================*/
@Composable
fun LogOutDialog(
    userViewModel: UserViewModel,
    onLogOutClicked: () -> Unit,
    onDismissAlert: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStore = (context).dataStore
    AlertDialog(
        onDismissRequest = {
            onDismissAlert()
        },
        text = {
            Text(
                text = "로그아웃\n하시겠습니까?",
                fontSize = 30.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        scope.launch {
                            userViewModel.resetUser()
                            dataStore.edit { preferences ->
                                preferences[DataStore.emailKey] = ""
                                preferences[DataStore.pwdKey] = ""
                            }
                            onLogOutClicked()
                        }
                    }
                ) {
                    Text(text = "확인", fontSize = 20.sp)
                }
                TextButton(
                    onClick = {
                        onDismissAlert()
                    }
                ) {
                    Text(text = "취소", fontSize = 20.sp)
                }
            }
        },
    )
}
