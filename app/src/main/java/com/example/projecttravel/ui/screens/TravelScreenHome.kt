package com.example.projecttravel.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projecttravel.R
import com.example.projecttravel.ui.screens.homepage.HomePage
import com.example.projecttravel.ui.screens.planroutegps.RouteGpsPage
import com.example.projecttravel.ui.screens.plantrip.PlanPage
import com.example.projecttravel.ui.screens.searchplace.SearchGpsPage
import com.example.projecttravel.ui.screens.selection.SelectPage
import com.example.projecttravel.ui.screens.testboard.TestBoardPage
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSearch
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class TravelScreen(@StringRes val title: Int) {
    Page1(title = R.string.page1),   // 각 화면의 제목 텍스트에 해당하는 각 열거형 케이스에 대한 리소스 값을 추가합
    Page2(title = R.string.page2),
    Page2A(title = R.string.pageGps),
    Page3(title = R.string.page3),
    Page3A(title = R.string.pageRoute),
    Page4(title = R.string.page4),
    Page5(title = R.string.page5),
    PageLoad(title = R.string.loading),
}
// working 2023-10-18
/** Composable that displays screens */
@Composable
fun TravelApp(
    selectViewModel: ViewModelSelect = viewModel(),
    searchViewModel: ViewModelSearch = viewModel(),
    planViewModel: ViewModelPlan = viewModel(),
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TravelScreen.valueOf(
        backStackEntry?.destination?.route ?: TravelScreen.Page1.name
    )
    /** All Screens => All contents */
    Scaffold(
        topBar = {
            TravelAppBar(
                currentScreen = currentScreen,
                navController = navController,  // TravelApp()의 navController 를 전달
                drawerState = drawerState,
                scope = scope,
            )
        },
    ) { innerPadding ->
        /** All pages ====================*/
        val selectUiState by selectViewModel.selectUiState.collectAsState()
        val searchUiState by searchViewModel.searchUiState.collectAsState()
        val planUiState by planViewModel.planUiState.collectAsState()

        NavHost(    // NavHost 컴포저블을 추가
            navController = navController,
            startDestination = TravelScreen.Page1.name,
            modifier = Modifier.padding(innerPadding)
        ) {     // 최종 매개변수에 빈 후행 람다를 전달
            /** 1. 로그인 첫번째 페이지 ====================*/
            composable(route = TravelScreen.Page1.name) {
                /** ModalNavigationDrawer must always be placed before any screens */
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContents(
                            navController = navController,
                            drawerState = drawerState,
                            scope = scope,
                        )
                    },
                ) {
                    HomePage(
                        onNextButtonClicked = {
                            navController.navigate(TravelScreen.Page2.name)
                        },
                        countryCardClicked = {
                            selectViewModel.setCountry(it) // 카드를 누르면 나라를 변경
                            selectViewModel.setCity(null) // 나라를 변경하면 다른 값들 초기화
                            selectViewModel.setInterest(null) // 나라를 변경하면 다른 값들 초기화
                            navController.navigate(TravelScreen.Page2.name)
                        },
                    )
                    /** Exit App when press BackHandler twice quickly */
                    ExitAppWhenBackOnPressed(drawerState)
                }
            }

            /** 2. 나라, 도시, 관광지 선택 화면 ====================*/
            composable(route = TravelScreen.Page2.name) {
                SelectPage(
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    selectUiState = selectUiState,
                    selectViewModel = selectViewModel, // 이 부분이 추가되어야 SelectPage 내에서 viewModel 코드가 돌아감!!!!!
                    onCancelButtonClicked = { navController.navigate(TravelScreen.Page1.name) },
                    onNextButtonClicked = { navController.navigate(TravelScreen.Page3.name) },
                    onGpsClicked = { navController.navigate(TravelScreen.Page2A.name) },
                )
                BackHandler(
                    enabled = drawerState.isClosed,
                    onBack = { navController.navigate(TravelScreen.Page1.name) },
                )
            }

            /** 2-1. GPS 선택 화면 ====================*/
            composable(route = TravelScreen.Page2A.name) {
                SearchGpsPage(
                    selectUiState = selectUiState,
                    searchUiState = searchUiState,
                    searchViewModel = searchViewModel,
                    selectViewModel = selectViewModel,
                    onBackButtonClicked = { navController.navigate(TravelScreen.Page2.name) },
                    updateUiPageClicked = { navController.navigate(TravelScreen.Page2A.name) },
                )
                BackHandler(
                    onBack = { navController.navigate(TravelScreen.Page2.name) },
                )
            }

            /** 3. 여행 플랜 짜기 화면 ====================*/
            composable(route = TravelScreen.Page3.name) {
                PlanPage(
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    onCancelButtonClicked = { navController.navigate(TravelScreen.Page2.name) },
                    onNextButtonClicked = {  },
                    onRouteClicked = { navController.navigate(TravelScreen.Page2.name) },
                )
            }

            /** 3-1. 경로 확인 화면 ====================*/
            composable(route = TravelScreen.Page3A.name) {
                RouteGpsPage(
                    planUiState = planUiState,
                    onBackButtonClicked = { navController.navigate(TravelScreen.Page3.name) },
                )
                BackHandler(
                    enabled = drawerState.isClosed,
                    onBack = { navController.navigate(TravelScreen.Page3.name) },
                )
            }

            /** 4. 게시판 임시 화면 ====================*/
            composable(route = TravelScreen.Page4.name) {
                TestBoardPage(
                    selectUiState = selectUiState,
                    selectViewModel = selectViewModel, // 이 부분이 추가되어야 SelectPage 내에서 viewModel 코드가 돌아감!!!!!
                    onCancelButtonClicked = { navController.navigate(TravelScreen.Page3.name) }
                )
                BackHandler(
                    enabled = drawerState.isClosed,
                    onBack = { navController.navigate(TravelScreen.Page3.name) },
                )
            }
        }

        /** DrawerMenu Screen closed when click phone's backButton */
        /** Must be placed beneath NavHost() to apply this BackHandler logic to all pages */
        BackHandler(
            enabled = drawerState.isOpen,
            onBack = { scope.launch { drawerState.close() } }
        )
    }
}

/** Exit App when drawerState.isClosed && press BackHandler twice quickly */
@Composable
fun ExitAppWhenBackOnPressed(
    drawerState: DrawerState,
) {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    // drawerState 가 닫힌 상태일 때 backPressedState 이 작동 된다
    BackHandler(enabled = drawerState.isClosed && backPressedState) {
        if (System.currentTimeMillis() - backPressedTime <= 700L) {
            // 뒤로 가기를 한 번 누르고  700L (0.7초 = 700밀리초) 안으로 한 번 더 눌러야 종료됨
            (context as Activity).finish()
        } else {
            backPressedState = true
            // 뒤로 가기를 한 번 누르면 나오는 안내문
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
