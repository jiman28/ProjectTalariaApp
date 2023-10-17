package com.example.projecttravel.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projecttravel.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * ModalNavigationDrawer Contents
 * */
@Composable
fun DrawerContents (
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    ModalDrawerSheet(
        modifier = Modifier.size(200.dp, 500.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
            modifier = Modifier.selectable(
                selected = false,
                onClick = {
//                            navController.navigate(TravelScreen.PageTwo.name)
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
                text = "User's name",
                fontSize = 25.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        TextButton(onClick = {
            navController.navigate(TravelScreen.Page2.name)
            scope.launch { drawerState.close() }
        }) {
            Text(text = "관광지 선택하기", fontSize = 25.sp)
        }
        TextButton(onClick = {
            navController.navigate(TravelScreen.Page3.name)
            scope.launch { drawerState.close() }
        }) {
            Text(text = "내가 고른 관광지", fontSize = 25.sp)
        }
//        TextButton(onClick = {
//            navController.navigate(TravelScreen.Page4.name)
//            scope.launch { drawerState.close() }
//        }) {
//            Text(text = "GPS 임시 페이지", fontSize = 25.sp)
//        }
//        TextButton(onClick = {
//            navController.navigate(TravelScreen.Page5.name)
//            scope.launch { drawerState.close() }
//        }) {
//            Text(text = "게시판 임시 페이지", fontSize = 25.sp)
//        }
    }
}