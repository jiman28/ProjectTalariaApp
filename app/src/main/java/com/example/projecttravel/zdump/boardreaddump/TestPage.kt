package com.example.projecttravel.ui.screens.boardread

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.viewmodels.PlanViewModel
import com.example.projecttravel.data.viewmodels.UserViewModel
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.boardlist.readapi.getReplyListMobile
import com.example.projecttravel.ui.screens.boardview.BoardsPageTabButtons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun TestPage (
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    planUiState: PlanUiState,
    planViewModel: PlanViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    navController: NavHostController,
    scope: CoroutineScope,
) {
    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }
    Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
    Column {
        if (userUiState.checkMyPlanList.isNotEmpty()) {
            Text(text = "pages = ${userUiState.checkMyPlanList.size}")

            Spacer(Modifier.size(10.dp))
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

            LazyColumn(
            ) {
                items(
                    items = userUiState.checkMyPlanList,
                    key = { board ->
                        board.id
                    }
                ) { board ->
                    Text(fontSize = 12.sp, text = "아티클 id : ${board.id}")
                    Text(fontSize = 12.sp, text = "아티클 planName : ${board.planName}")
                    Text(fontSize = 12.sp, text = "아티클 endDay : ${board.endDay}")
                    Text(fontSize = 12.sp, text = "아티클 startDay : ${board.startDay}")
                    Text(fontSize = 12.sp, text = "아티클 email : ${board.email}")
                    Spacer(Modifier.size(10.dp))
                    board.plans.forEach { dates ->
                        Text(fontSize = 12.sp, text = "께획 date : ${dates.date}")
                        Spacer(Modifier.size(10.dp))
                        dates.list.forEach { attrs ->
                            Text(fontSize = 12.sp, text = "attrs email : ${attrs.name}")
                            Text(fontSize = 12.sp, text = "attrs email : ${attrs.cityId}")
                            Spacer(Modifier.size(10.dp))
                        }
                    }
                    Spacer(Modifier.size(10.dp))

                    Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                }
            }


        }
    }

    Column {

        Column {
            BoardsPageTabButtons(
                boardPageUiState = boardPageUiState,
                boardPageViewModel = boardPageViewModel,
            )
        }

        when (boardPageUiState.currentSelectedBoard) {
            R.string.boardTabTitle -> {
                if (boardPageUiState.currentBoardList != null) {
                    Column {
//            Text(text = "page = ${boardPageUiState.currentBoardList.page}")
//            Text(text = "kw = ${boardPageUiState.currentBoardList.kw}")
//            Text(text = "type = ${boardPageUiState.currentBoardList.type}")

                        Text(text = "pages = ${boardPageUiState.currentBoardList.pages}")

                        Spacer(Modifier.size(10.dp))
                        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                        LazyColumn(
                        ) {
                            items(
                                items = boardPageUiState.currentBoardList.list,
                                key = { board ->
                                    board.articleNo
                                }
                            ) { board ->
                                Text(fontSize = 12.sp, text = "아티클 넘버 : ${board.articleNo.toString()}")
                                Text(fontSize = 12.sp, text = board.title)
                                Text(fontSize = 12.sp, text = board.content)
                                Text(fontSize = 12.sp, text = board.views.toString())
                                Text(fontSize = 12.sp, text = board.writeDate.toString())
                                Text(fontSize = 12.sp, text = board.writeId)
                                Spacer(Modifier.size(10.dp))
                                Text(fontSize = 12.sp, text = board.user.id.toString())
                                Text(fontSize = 12.sp, text = board.user.email)
                                board.user.password?.let { Text(fontSize = 12.sp, text = it) }
                                Spacer(Modifier.size(10.dp))
                                val counter = boardPageUiState.currentBoardList.replyCount.find { it.articleNo == board.articleNo }

                                Text(fontSize = 20.sp, text = "아티클 넘버 : ${counter?.articleNo}")
                                Text(fontSize = 20.sp, text = "댓글개수 : ${counter?.replyCount}")



                                TextButton(onClick = {
                                    scope.launch {
                                        isLoadingState = true
                                        val callReply = CallReply(tabtitle = "리뷰모음", articleNo = board.articleNo.toString())
                                        val isReplyDeferred = async { getReplyListMobile(callReply) }
                                        // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                        val isReplyComplete = isReplyDeferred.await()
                                        // 모든 작업이 완료되었을 때만 실행합니다.
                                        if (isReplyComplete != null) {
                                            boardPageViewModel.setReplyList(isReplyComplete)
                                            isLoadingState = null
                                            navController.navigate(TravelScreen.PageTest2.name)
                                        } else {
                                            isLoadingState = false
                                        }
                                    }
                                }) {
                                    Text(text = "reply 테스트 reply 테스트", fontSize = 25.sp)
                                }

                                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                            }
                        }
                    }
                }
            }
            R.string.companyTabTitle -> {
                if (boardPageUiState.currentCompanyList != null) {
                    Column {
//            Text(text = "page = ${boardPageUiState.currentBoardList.page}")
//            Text(text = "kw = ${boardPageUiState.currentBoardList.kw}")
//            Text(text = "type = ${boardPageUiState.currentBoardList.type}")

                        Text(text = "pages = ${boardPageUiState.currentCompanyList.pages}")

                        Spacer(Modifier.size(10.dp))
                        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                        LazyColumn(
                        ) {
                            items(
                                items = boardPageUiState.currentCompanyList.list,
                                key = { board ->
                                    board.articleNo
                                }
                            ) { board ->
                                Text(fontSize = 12.sp, text = "아티클 넘버 : ${board.articleNo.toString()}")
                                Text(fontSize = 12.sp, text = board.title)
                                Text(fontSize = 12.sp, text = board.content)
                                Text(fontSize = 12.sp, text = board.views.toString())
                                Text(fontSize = 12.sp, text = board.writeDate.toString())
                                Text(fontSize = 12.sp, text = board.writeId)
                                Spacer(Modifier.size(10.dp))
                                Text(fontSize = 12.sp, text = board.user.id.toString())
                                Text(fontSize = 12.sp, text = board.user.email)
                                board.user.password?.let { Text(fontSize = 12.sp, text = it) }
                                Spacer(Modifier.size(10.dp))
                                val counter = boardPageUiState.currentCompanyList.replyCount.find { it.articleNo == board.articleNo }

                                Text(fontSize = 20.sp, text = "아티클 넘버 : ${counter?.articleNo}")
                                Text(fontSize = 20.sp, text = "댓글개수 : ${counter?.replyCount}")



                                TextButton(onClick = {
                                    scope.launch {
                                        isLoadingState = true
                                        val callReply = CallReply(tabtitle = "동행자구인", articleNo = board.articleNo.toString())
                                        val isReplyDeferred = async { getReplyListMobile(callReply) }
                                        // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                        val isReplyComplete = isReplyDeferred.await()
                                        // 모든 작업이 완료되었을 때만 실행합니다.
                                        if (isReplyComplete != null) {
                                            boardPageViewModel.setReplyList(isReplyComplete)
                                            isLoadingState = null
                                            navController.navigate(TravelScreen.PageTest2.name)
                                        } else {
                                            isLoadingState = false
                                        }
                                    }
                                }) {
                                    Text(text = "reply 테스트 reply 테스트", fontSize = 25.sp)
                                }

                                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                            }
                        }
                    }
                }
            }
            R.string.tradeTabTitle -> {
                if (boardPageUiState.currentTradeList != null) {
                    Column {
//            Text(text = "page = ${boardPageUiState.currentBoardList.page}")
//            Text(text = "kw = ${boardPageUiState.currentBoardList.kw}")
//            Text(text = "type = ${boardPageUiState.currentBoardList.type}")

                        Text(text = "pages = ${boardPageUiState.currentTradeList.pages}")

                        Spacer(Modifier.size(10.dp))
                        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                        LazyColumn(
                        ) {
                            items(
                                items = boardPageUiState.currentTradeList.list,
                                key = { board ->
                                    board.articleNo
                                }
                            ) { board ->
                                Text(fontSize = 12.sp, text = "아티클 넘버 : ${board.articleNo.toString()}")
                                Text(fontSize = 12.sp, text = board.title)
                                Text(fontSize = 12.sp, text = board.content)
                                Text(fontSize = 12.sp, text = board.views.toString())
                                Text(fontSize = 12.sp, text = board.writeDate.toString())
                                Text(fontSize = 12.sp, text = board.writeId)
                                Spacer(Modifier.size(10.dp))
                                Text(fontSize = 12.sp, text = board.user.id.toString())
                                Text(fontSize = 12.sp, text = board.user.email)
                                board.user.password?.let { Text(fontSize = 12.sp, text = it) }
                                Spacer(Modifier.size(10.dp))
                                val counter = boardPageUiState.currentTradeList.replyCount.find { it.articleNo == board.articleNo }

                                Text(fontSize = 20.sp, text = "아티클 넘버 : ${counter?.articleNo}")
                                Text(fontSize = 20.sp, text = "댓글개수 : ${counter?.replyCount}")



                                TextButton(onClick = {
                                    scope.launch {
                                        isLoadingState = true
                                        val callReply = CallReply(tabtitle = "거래시스템", articleNo = board.articleNo.toString())
                                        val isReplyDeferred = async { getReplyListMobile(callReply) }
                                        // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                        val isReplyComplete = isReplyDeferred.await()
                                        // 모든 작업이 완료되었을 때만 실행합니다.
                                        if (isReplyComplete != null) {
                                            boardPageViewModel.setReplyList(isReplyComplete)
                                            isLoadingState = null
                                            navController.navigate(TravelScreen.PageTest2.name)
                                        } else {
                                            isLoadingState = false
                                        }
                                    }
                                }) {
                                    Text(text = "reply 테스트 reply 테스트", fontSize = 25.sp)
                                }

                                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

                            }
                        }
                    }
                }
            }
        }
    }
}

//            Row(
//                verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
//                horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
//                modifier = Modifier.padding(10.dp)
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
//                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text(text = board.articleNo)
//                }
//                Column(
//                    modifier = Modifier.weight(7f)
//                ) {
//                    Row (
//                        verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
//                        horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
//                    ) {
//                        Column (
//                            modifier = Modifier.weight(8f)
//                        ) {
//                            TextButton(
//                                onClick = {
//                                    viewCounter(tabtitle, board.articleNo)
//                                    onBoardClicked()
//                                    boardSelectViewModel.setSelectedBoard(board)
//                                }
//                            ) {
////                                        Text(fontSize = 20.sp, text = board.title)
//                                EllipsisTextBoard(text = board.title, maxLength = 10, modifier = Modifier.fillMaxWidth())
//                            }
//                        }
//                        Column (
//                            verticalArrangement = Arrangement.Bottom, // 수직 가운데 정렬
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .weight(2f),
//                        ) {
//                            Row (
//                                verticalAlignment = Alignment.Bottom, // 수직 가운데 정렬
//                                horizontalArrangement = Arrangement.End, // 수평 가운데 정렬
//                                modifier = Modifier.fillMaxWidth()
//                            ) {
//                                val filteredReplyList = replyUiState?.replyList?.filter {
//                                    it.boardEntity == board.articleNo
//                                }
//                                Icon(
//                                    modifier = Modifier.size(15.dp),
//                                    imageVector = Icons.Filled.Comment,
//                                    contentDescription = "comments"
//                                )
//                                Spacer(modifier = Modifier.padding(2.dp))
//                                Text(fontSize = 15.sp, text = "${filteredReplyList?.size ?: 0}") // 크기를 출력
//                            }
//                        }
//                    }
//                    Row (verticalAlignment = Alignment.CenterVertically,) {
//                        Row (
//                            verticalAlignment = Alignment.CenterVertically,
//                        ) {
//                            Spacer(modifier = Modifier.padding(5.dp))
//                            Icon(
//                                modifier = Modifier.size(15.dp),
//                                imageVector = Icons.Filled.AccountCircle,
//                                contentDescription = "Account"
//                            )
//                            Spacer(modifier = Modifier.padding(2.dp))
//                            Text(fontSize = 12.sp, text = board.writeId)
//                        }
//                        Row (
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.End
//                        ){
//                            Icon(
//                                modifier = Modifier.size(15.dp),
//                                imageVector = Icons.Filled.AccessTime,
//                                contentDescription = "WriteDate"
//                            )
//                            Spacer(modifier = Modifier.padding(2.dp))
//                            Text(fontSize = 12.sp, text = board.writeDate)
//
//                            Spacer(modifier = Modifier.padding(5.dp))
//
//                            Icon(
//                                modifier = Modifier.size(15.dp),
//                                imageVector = Icons.Filled.RemoveRedEye,
//                                contentDescription = "Views"
//                            )
//                            Spacer(modifier = Modifier.padding(2.dp))
//
//
//
//                        }