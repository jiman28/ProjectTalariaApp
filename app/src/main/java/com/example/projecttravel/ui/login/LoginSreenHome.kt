package com.example.projecttravel.ui.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.projecttravel.R
import com.example.projecttravel.ui.login.Forms.LoginForm
import com.example.projecttravel.ui.login.Forms.SignForm
import com.example.projecttravel.ui.screens.TravelScreen

enum class LoginScreen(@StringRes val title: Int) {
    Page1(title = R.string.pageLogin),   // 각 화면의 제목 텍스트에 해당하는 각 열거형 케이스에 대한 리소스 값을 추가합
    Page2(title = R.string.pageSignIn),
}


@Composable
fun LoginSreenHome(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TravelScreen.valueOf(
        backStackEntry?.destination?.route ?: TravelScreen.Page1.name
    )

    NavHost(
        // NavHost 컴포저블을 추가
        navController = navController,
        startDestination = TravelScreen.Page1.name,
        modifier = Modifier,
    ) {
        // 최종 매개변수에 빈 후행 람다를 전달
        /** 1. 로그인 첫번째 페이지 ====================*/
        composable(route = TravelScreen.Page1.name) {
            LoginForm(
                onNextButtonClicked = {
                    navController.navigate(TravelScreen.Page2.name)
                },
            )
            /** Exit App when press BackHandler twice quickly */
            ExitAppWhenBackOnPressedLogin()
        }

        /** 2. 나라, 도시, 관광지 선택 화면 ====================*/
        composable(route = TravelScreen.Page2.name) {
            SignForm(
                onCancelButtonClicked = {
                    navController.navigate(TravelScreen.Page1.name)
                },
            )
        }
    }
}

/** Exit App when drawerState.isClosed && press BackHandler twice quickly */
@Composable
fun ExitAppWhenBackOnPressedLogin(
) {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L
    // drawerState 가 닫힌 상태일 때 backPressedState 이 작동 된다
    BackHandler(enabled = backPressedState) {
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
