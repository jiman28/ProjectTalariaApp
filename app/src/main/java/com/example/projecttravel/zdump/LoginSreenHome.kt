package com.example.projecttravel.zdump

//import com.example.projecttravel.zdump.dtsample.SettingPage

//enum class LoginScreen(@StringRes val title: Int) {
//
//    Page3(title = R.string.pageTestDS),
//}
//
//@Composable
//fun LoginSreenHome(
//    navController: NavHostController = rememberNavController(),
//) {
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = TravelScreen.valueOf(
//        backStackEntry?.destination?.route ?: TravelScreen.Page1.name
//    )
//
//    NavHost(
//        // NavHost 컴포저블을 추가
//        navController = navController,
//        startDestination = TravelScreen.Page1.name,
//        modifier = Modifier,
//    ) {
//        // 최종 매개변수에 빈 후행 람다를 전달
//        /** 1. 로그인 첫번째 페이지 ====================*/
//
//
////        /** 2. 나라, 도시, 관광지 선택 화면 ====================*/
////        composable(route = TravelScreen.Page3.name) {
////            SettingPage()
////        }
//    }
//}
//
///** Exit App when drawerState.isClosed && press BackHandler twice quickly */
//@Composable
//fun ExitAppWhenBackOnPressedLogin(
//) {
//    val context = LocalContext.current
//    var backPressedState by remember { mutableStateOf(true) }
//    var backPressedTime = 0L
//    // drawerState 가 닫힌 상태일 때 backPressedState 이 작동 된다
//    BackHandler(enabled = backPressedState) {
//        if (System.currentTimeMillis() - backPressedTime <= 700L) {
//            // 뒤로 가기를 한 번 누르고  700L (0.7초 = 700밀리초) 안으로 한 번 더 눌러야 종료됨
//            (context as Activity).finish()
//        } else {
//            backPressedState = true
//            // 뒤로 가기를 한 번 누르면 나오는 안내문
//            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
//        }
//        backPressedTime = System.currentTimeMillis()
//    }
//}
