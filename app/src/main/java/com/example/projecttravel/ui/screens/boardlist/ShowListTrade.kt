package com.example.projecttravel.ui.screens.boardlist//package com.example.projecttravel.ui.screens.boardview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.repositories.board.viewmodels.BoardUiState
import com.example.projecttravel.data.repositories.board.viewmodels.TradeUiState
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.boardwrite.writeapi.EllipsisTextBoard
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.model.TradeList
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalErrorScreen
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.GlobalLoadingScreen
import com.example.projecttravel.ui.screens.boardlist.readapi.getAllBoardDefault
import com.example.projecttravel.ui.screens.boardlist.readapi.getReplyListMobile
import com.example.projecttravel.ui.screens.boardlist.readapi.getTradeListMobile
import com.example.projecttravel.ui.screens.boardlist.readapi.viewCounter
import com.example.projecttravel.ui.screens.boardview.ListBoardEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun ShowListTrade(
    tradeUiState: TradeUiState,
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    onBoardClicked: () -> Unit,
    onResetButtonClicked: () -> Unit,
    retryAction: () -> Unit,
) {
    when (tradeUiState) {
        is TradeUiState.Loading -> GlobalLoadingScreen()
        is TradeUiState.TradeSuccess ->
            if (tradeUiState.tradeList != null && tradeUiState.tradeList.list.isNotEmpty()) {
                ListTradeEntity(
                    tradeList = tradeUiState.tradeList,
                    userUiState = userUiState,
                    planUiState = planUiState,
                    boardPageUiState = boardPageUiState,
                    boardPageViewModel = boardPageViewModel,
                    onBoardClicked = onBoardClicked,
                    onResetButtonClicked = onResetButtonClicked,
                )
            } else {
                NoArticlesFoundScreen()
            }

        else -> GlobalErrorScreen(retryAction)
    }
}

@Composable
fun ListTradeEntity(
    tradeList: TradeList,
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    onBoardClicked: () -> Unit,
    onResetButtonClicked: () -> Unit,
) {


    val scope = rememberCoroutineScope()

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

    Spacer(modifier = Modifier.padding(5.dp))
    val tabtitle = stringResource(boardPageUiState.currentSelectedBoardTab)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
//        item { Text(text = "확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인확인") }
        items(
            items = tradeList.list,
            key = { board ->
                board.articleNo
            }
        ) { board ->
            Card(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(5.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                    horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                    modifier = Modifier.padding(10.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = board.articleNo.toString())
                    }
                    Column(
                        modifier = Modifier.weight(7f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                        ) {
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                TextButton(
                                    onClick = {
//                                        isLoadingState = true
//                                        val result: Boolean = try {
//                                            viewCounter(tabtitle, board.articleNo.toString())
//                                            boardPageViewModel.setViewBoard(board)
//                                            boardPageViewModel.setSelectedArtcNum(board.articleNo.toString())
//                                            true // 성공했을 경우 true 반환
//                                        } catch (e: Exception) {
//                                            // 예외 처리 로직 추가
//                                            false // 실패했을 경우 false 반환
//                                        }
//
//                                        if (result) {
//                                            isLoadingState = null
//                                            onBoardClicked()
//                                        } else {
//                                            isLoadingState = false
//                                        }

                                        scope.launch {
                                            isLoadingState = true
                                            val callReply = CallReply(
                                                tabtitle = tabtitle,
                                                articleNo = board.articleNo.toString()
                                            )
                                            val isReplyDeferred =
                                                async { getReplyListMobile(callReply) }
                                            // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                            val isReplyComplete = isReplyDeferred.await()
                                            // 모든 작업이 완료되었을 때만 실행합니다.
                                            if (isReplyComplete != null) {
                                                viewCounter(tabtitle, board.articleNo.toString())
                                                boardPageViewModel.setViewBoard(board)
//                                                boardPageViewModel.setSelectedTrade(board)
                                                boardPageViewModel.setReplyList(isReplyComplete)
                                                isLoadingState = null
                                                onBoardClicked()
                                            } else {
                                                isLoadingState = false
                                            }
                                        }
                                    }
                                ) {
//                                        Text(fontSize = 20.sp, text = board.title)
                                    EllipsisTextBoard(
                                        text = board.title,
                                        maxLength = 10,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                            Column(
                                verticalArrangement = Arrangement.Bottom, // 수직 가운데 정렬
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(2f),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Bottom, // 수직 가운데 정렬
                                    horizontalArrangement = Arrangement.End, // 수평 가운데 정렬
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    val replyListCounter =
                                        tradeList.replyCount.find { it.articleNo == board.articleNo }
                                    Icon(
                                        modifier = Modifier.size(15.dp),
                                        imageVector = Icons.Filled.Comment,
                                        contentDescription = "comments"
                                    )
                                    Spacer(modifier = Modifier.padding(2.dp))
                                    Text(
                                        fontSize = 15.sp,
                                        text = "${replyListCounter?.replyCount ?: 0}"
                                    ) // 크기를 출력
                                }
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Spacer(modifier = Modifier.padding(5.dp))
                                val userImage = board.user.picture
                                if (userImage != null) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(15.dp)
                                            .clip(RoundedCornerShape(50.dp)),
                                        model = ImageRequest.Builder(context = LocalContext.current)
                                            .data(userImage)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(id = R.drawable.icon_user),
                                        placeholder = painterResource(id = R.drawable.loading_img)
                                    )
                                } else {
                                    Icon(
                                        modifier = Modifier.size(15.dp),
                                        imageVector = Icons.Filled.AccountCircle,
                                        contentDescription = "Account"
                                    )
                                }
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(fontSize = 12.sp, text = board.writeId)
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.AccessTime,
                                    contentDescription = "WriteDate"
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(fontSize = 12.sp, text = board.writeDate.substring(0 until 10))

                                Spacer(modifier = Modifier.padding(5.dp))

                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.RemoveRedEye,
                                    contentDescription = "Views"
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(fontSize = 12.sp, text = board.views.toString())
                            }
                        }
                    }
                }
            }
        }
        item {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyRow {
                    items(tradeList.pages) { index ->
                        // (index = boardList.pages - 1)까지의 값을 가지게 됩니다.
                        val callBoard = CallBoard(
                            kw = boardPageUiState.currentSearchKeyWord,
                            page = index,
                            type = stringResource(boardPageUiState.currentSearchType),
                            email = if (userUiState.checkOtherUser != null) userUiState.checkOtherUser.email else ""
                        )
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .background(
                                    if (boardPageUiState.currentTradePage == index) Color(0xFF005FAF) else Color.White
                                )
                                .clickable {
                                    Log.d("jiman", "Clicked on page $index")
                                    scope.launch {
                                        isLoadingState = true

                                        val isDeferred = async { getTradeListMobile(callBoard) }
                                        val isComplete = isDeferred.await()
                                        // 모든 작업이 완료되었을 때만 실행합니다.
                                        if (isComplete != null) {
                                            isLoadingState = null
                                            boardPageViewModel.setTradeList(isComplete)
                                            boardPageViewModel.setTradePage(index)
                                            onResetButtonClicked()
                                        } else {
                                            isLoadingState = false
                                        }
                                    }
                                }
                        ) {
                            Text(
                                text = (index + 1).toString(), // 1부터 시작하도록 표시
                                color = if (boardPageUiState.currentTradePage == index) Color.White else Color(0xFF005FAF),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}
