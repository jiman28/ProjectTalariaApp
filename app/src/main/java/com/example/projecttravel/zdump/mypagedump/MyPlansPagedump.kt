package com.example.projecttravel.zdump.mypagedump

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.projecttravel.R
//import com.example.projecttravel.data.uistates.UserUiState
//import com.example.projecttravel.ui.screens.GlobalLoadingDialog
//import com.example.projecttravel.ui.screens.TextMsgErrorDialog
//import com.example.projecttravel.ui.screens.infome.infoapi.getPeopleLikeMe
//import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
//import com.example.projecttravel.ui.screens.viewmodels.user.PlanListUiState
//import com.example.projecttravel.ui.screens.viewmodels.user.ViewModelListPlan
//import kotlinx.coroutines.async
//import kotlinx.coroutines.launch
//
//@Composable
//fun MyPlansPageDump(
//    userUiState: UserUiState,
//    userViewModel: ViewModelUser,
//    onNextButtonClicked: () -> Unit,
//) {
//    val scope = rememberCoroutineScope()
//
//    val userPlanListViewModel: ViewModelListPlan = viewModel(factory = ViewModelListPlan.PlanListFactory)
//
//    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
//    var peopleErrorMsg by remember { mutableStateOf("") }
//
//    Surface {
//        when (isLoadingState) {
//            true -> GlobalLoadingDialog()
//            false -> TextMsgErrorDialog(
//                txtErrorMsg = peopleErrorMsg,
//                onDismiss = { isLoadingState = null })
//
//            else -> isLoadingState = null
//        }
//    }
//
//    Column {
//        Column {
//            Button(
//                onClick = {
//                    scope.launch {
//                        isLoadingState = true
//                        val peopleDeferred = async { userUiState.currentLogin?.let { getPeopleLikeMe(it) } }
//                        val peopleComplete = peopleDeferred.await()
//                        if (peopleComplete != null) {
//                            isLoadingState = null
//                            userViewModel.setLikeUsers(peopleComplete)
//                            onNextButtonClicked()
//                        } else {
//                            peopleErrorMsg = "에러터짐"
//                            isLoadingState = false
//                        }
//                    }
//                },
//            ) {
//                Text("Sign in")
//            }
//        }
//
//        Column {
////            if (userUiState.checkOtherUser != null) {
////                Row {
////                    userUiState.currentLogin?.let { Text(text = it.name, modifier = Modifier.padding(5.dp)) }
////                    userUiState.currentLogin?.let { Text(text = it.id, modifier = Modifier.padding(5.dp)) }
////                    userUiState.currentLogin?.let { Text(text = it.email, modifier = Modifier.padding(5.dp)) }
////                    if (userUiState.currentLogin?.picture != null) Text(text = userUiState.currentLogin.picture)
////                }
////            }
//        }
//
//        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//
//        /** ================================================== */
//        /** UserInfos */
//        Column(
//            modifier = Modifier
//                .padding(3.dp),
//            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
//            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
//        ) {
//            val planListUiState =
//                (userPlanListViewModel.planListUiState as? PlanListUiState.PlanListSuccess)
//            if (planListUiState != null) {
//                MyPlanListDump(
//                    planListUiState = planListUiState,
//                    contentPadding = PaddingValues(0.dp),
//                )
//            }
//        }
//    }
//}
