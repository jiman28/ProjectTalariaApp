package com.example.projecttravel.ui.screens.boardview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.model.CheckOtherUserById
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.infome.infoapi.getUserPageById
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import com.example.projecttravel.model.AllBoardsEntity
import com.example.projecttravel.model.BoardEntity
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.CompanyEntity
import com.example.projecttravel.model.TradeEntity
import com.example.projecttravel.ui.screens.boardlist.readapi.getAllBoardDefault
import com.example.projecttravel.ui.screens.infome.infoapi.callMyInterest
import com.example.projecttravel.ui.screens.infome.infoapi.callMyPlanList
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Composable
fun UserDropDownMenu(
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    userViewModel: UserViewModel,
    onUserButtonClicked: () -> Unit,
){
    val scope = rememberCoroutineScope()

    val currentWriteId: String = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.writeId
        is CompanyEntity -> boardPageUiState.selectedViewBoard.writeId
        is TradeEntity -> boardPageUiState.selectedViewBoard.writeId
        else -> ""
    }
    val currentUserId: String = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.user.id.toString()
        is CompanyEntity -> boardPageUiState.selectedViewBoard.user.id.toString()
        is TradeEntity -> boardPageUiState.selectedViewBoard.user.id.toString()
        else -> ""
    }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

// 1. DropDownMenu의 펼쳐짐 상태 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

// 2. DropDownMenu의 Expanded 상태를 변경하기 위한 버튼 정의
    TextButton(
        modifier = Modifier,
        onClick = { isDropDownMenuExpanded = true }
    ) {
        Text(
            text = currentWriteId
        )
    }

    // 3. DropDownMenu 정의
    DropdownMenu(
        modifier = Modifier.wrapContentSize(),
        expanded = isDropDownMenuExpanded,
        offset = DpOffset(0.dp, 0.dp), // Dropdown Menu 의 위치 조정
        onDismissRequest = { isDropDownMenuExpanded = false },
    ) {
        Column(
            modifier = Modifier.clickable {
                isDropDownMenuExpanded = false // 메 // 뉴 닫기
                scope.launch {
                    val checkOtherUserById = CheckOtherUserById(
                        id = currentUserId,
                    )
                    isLoadingState = true
                    // 비동기 작업을 시작하고 결과(return)를 받아오기 위한 Deferred 객체를 생성합니다.
                    val otherIdDeferred = async { getUserPageById(checkOtherUserById) }
                    // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                    val isOtherIdComplete = otherIdDeferred.await()
                    // 모든 작업이 완료되었을 때만 실행합니다.
                    if (isOtherIdComplete != null) {
                        val callBoardYou = CallBoard(
                            kw = "",
                            page = 0,
                            type = "",
                            email = isOtherIdComplete.email
                        )
                        val isDeferredInterest = async { callMyInterest(isOtherIdComplete) }
                        val isDeferredPlan = async { callMyPlanList(isOtherIdComplete) }
                        val isDeferredBoard = async { getAllBoardDefault(callBoardYou,boardPageViewModel,scope) }
                        val isCompleteInterest = isDeferredInterest.await()
                        val isCompletePlan = isDeferredPlan.await()
                        val isCompleteBoard = isDeferredBoard.await()
                        // 모든 작업이 완료되었을 때만 실행합니다.
                        if (isCompleteBoard && isCompleteInterest != null ) {
                            isLoadingState = null
                            userViewModel.setUserPageInfo(isOtherIdComplete)
                            userViewModel.setUserInterest(isCompleteInterest)
                            userViewModel.setUserPlanList(isCompletePlan)
                            userViewModel.previousScreenWasPageOneA(true)
                            userViewModel.setUserPageInfo(isOtherIdComplete)
                            userViewModel.previousScreenWasPageOneA(true)
                            onUserButtonClicked()
                        } else {
                            isLoadingState = false
                        }
                    } else {
                        isLoadingState = false
                    }
                }
            },
        ) {
            Text(
                text = "${currentWriteId}의 정보를 확인합니다",
                fontSize = 20.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        }
    }
}
