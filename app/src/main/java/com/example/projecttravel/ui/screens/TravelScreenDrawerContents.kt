package com.example.projecttravel.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.repositories.board.viewmodels.BoardViewModel
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.uistates.viewmodels.PlanViewModel
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.ui.screens.auth.datastore.DataStore
import com.example.projecttravel.ui.screens.auth.datastore.DataStore.Companion.dataStore
import com.example.projecttravel.ui.screens.boardlist.readapi.getAllBoardDefault
import com.example.projecttravel.ui.screens.infome.infoapi.callMyInterest
import com.example.projecttravel.ui.screens.infome.infoapi.callMyPlanList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ModalNavigationDrawer Contents
 * */
@Composable
fun DrawerContents(
    onLogOutClicked: () -> Unit,
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    planViewModel: PlanViewModel,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    boardViewModel: BoardViewModel,
) {
    val context = LocalContext.current

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
        modifier = Modifier.size(280.dp, 600.dp),
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
            Spacer(modifier = Modifier.padding(10.dp))
            if (userUiState.currentLogin?.picture != null) {
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(userUiState.currentLogin.picture)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.icon_user),
                    placeholder = painterResource(id = R.drawable.loading_img)
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.icon_user),
                    contentDescription = "icon_user",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp)
                )

            }
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "${userUiState.currentLogin?.name} 님,\n환영합니다",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        if (userUiState.currentLogin != null) {
            val callBoardMe = CallBoard(
                kw = "",
                page = 0,
                type = stringResource(boardPageUiState.currentSearchType),
                email = userUiState.currentLogin.email
            )
            val userResponse = userUiState.currentLogin
            Row(
                verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
            ) {
                Spacer(modifier = Modifier.padding(10.dp))
                Image(
                    painter = painterResource(R.drawable.icon_mypage),
                    contentDescription = "icon_mypage",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(2.dp))
                TextButton(onClick = {
                    scope.launch {
                        drawerState.close()
                        isLoadingState = true
                        val isDeferredInterest = async { callMyInterest(userResponse) }
                        val isDeferredPlan = async { callMyPlanList(userResponse) }
                        val isDeferredBoard =
                            async { getAllBoardDefault(callBoardMe, boardPageViewModel, scope) }
                        val isCompleteInterest = isDeferredInterest.await()
                        val isCompletePlan = isDeferredPlan.await()
                        val isCompleteBoard = isDeferredBoard.await()
                        // 모든 작업이 완료되었을 때만 실행합니다.
                        if (isCompleteBoard && isCompleteInterest != null) {
                            isLoadingState = null
                            userViewModel.setUserInterest(isCompleteInterest)
                            userViewModel.setUserPlanList(isCompletePlan)
                            userViewModel.previousScreenWasPageOneA(true)
                            userViewModel.setUserPageInfo(userUiState.currentLogin)
                            navController.navigate(TravelScreen.Page1A.name)
                        } else {
                            isLoadingState = false
                        }
                    }
                }) {
                    Text(
                        text = "MY PAGE",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,  // font 의 굵기
                        fontFamily = DefaultAppFontContent(),  // font 의 글씨체(커스텀)
                        style = MaterialTheme.typography.titleLarge,  //font 의 스타일)
                    )
                }
            }
        } else {
            Text(text = "로그인 오류", fontSize = 25.sp)
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(R.drawable.icon_logout),
                contentDescription = "icon_logout",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(2.dp))
            TextButton(onClick = {
                isLogOutState = true
                scope.launch {
                    drawerState.close()
                }
            }) {
                Text(
                    text = "LOGOUT",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,  // font 의 굵기
                    fontFamily = DefaultAppFontContent(),  // font 의 글씨체(커스텀)
                    style = MaterialTheme.typography.titleLarge,  //font 의 스타일)
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(R.drawable.talaria),
                contentDescription = "talaria",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(2.dp))
            TextButton(onClick = {
                userViewModel.previousScreenWasPageOneA(false)
                navController.navigate(TravelScreen.Page2.name)
                scope.launch { drawerState.close() }
            }) {
                Text(
                    text = "PLAN YOUR TRIP",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,  // font 의 굵기
                    fontFamily = DefaultAppFontContent(),  // font 의 글씨체(커스텀)
                    style = MaterialTheme.typography.titleLarge,  //font 의 스타일)
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(R.drawable.icon_board),
                contentDescription = "icon_board",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(2.dp))
            val callBoardBoard = CallBoard(
                kw = "",
                page = 0,
                type = stringResource(boardPageUiState.currentSearchType),
                email = ""
            )
            TextButton(onClick = {
                scope.launch {
                    drawerState.close()
                    boardViewModel.getBoardList(callBoardBoard)
                    boardViewModel.getCompanyList(callBoardBoard)
                    boardViewModel.getTradeList(callBoardBoard)
                    navController.navigate(TravelScreen.Page4.name)
                }
//                scope.launch {
//                    drawerState.close()
//                    isLoadingState = true
//                    val isDeferred =
//                        async { getAllBoardDefault(callBoardBoard, boardPageViewModel, scope) }
//                    val isComplete = isDeferred.await()
//                    // 모든 작업이 완료되었을 때만 실행합니다.
//                    if (isComplete) {
//                        isLoadingState = null
//                        userViewModel.previousScreenWasPageOneA(false)
//                        navController.navigate(TravelScreen.Page4.name)
//                    } else {
//                        isLoadingState = false
//                    }
//                }
            }) {
                Text(
                    text = "BOARD",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,  // font 의 굵기
                    fontFamily = DefaultAppFontContent(),  // font 의 글씨체(커스텀)
                    style = MaterialTheme.typography.titleLarge,  //font 의 스타일)
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(R.drawable.logo_google_maps),
                contentDescription = "logo_google_maps",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        scope.launch {
                            drawerState.close()
                            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"))
                            // Check if there's a mapping app available before starting the activity
                            context.startActivity(mapIntent)
                        }
                    }
            )
            Spacer(modifier = Modifier.padding(2.dp))
        }

        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(R.drawable.logo_skyscanner),
                contentDescription = "logo_skyscanner",
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            drawerState.close()
                            val url = "https://www.skyscanner.co.kr/"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            // Check if there's a web browser available before starting the activity
                            context.startActivity(intent)
                        }
                    }
            )
            Spacer(modifier = Modifier.padding(2.dp))
        }

        /** Test ==================== ==================== ==================== ==================== ====================*/
        Spacer(modifier = Modifier.padding(2.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
        Spacer(modifier = Modifier.padding(2.dp))
        val testBoardBoard = CallBoard(
            kw = "",
            page = 0,
            type = stringResource(boardPageUiState.currentSearchType),
            email = ""
        )
        TextButton(onClick = {
            boardViewModel.getBoardList(testBoardBoard)
            navController.navigate(TravelScreen.PageTest.name)
        }) {
            Text(text = "테스트페이지", fontSize = 25.sp)
        }
        /** Test ==================== ==================== ==================== ==================== ====================*/

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
