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
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TravelScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun TestPageSecond (
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

    if (boardPageUiState.currentReplyList.isNotEmpty()) {
        Column {
//            Text(text = "page = ${boardPageUiState.currentBoardList.page}")
//            Text(text = "kw = ${boardPageUiState.currentBoardList.kw}")
//            Text(text = "type = ${boardPageUiState.currentBoardList.type}")


//            Text(text = "pages = ${boardPageUiState.currentBoardList.pages}")
            Spacer(Modifier.size(10.dp))
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

            LazyColumn(
            ) {
                items(
                    items = boardPageUiState.currentReplyList,
                    key = { reply ->
                        reply.replyNo
                    }
                ) { reply ->
                    Text(fontSize = 12.sp, text = reply.replyNo)
                    Text(fontSize = 12.sp, text = reply.replyContent)
                    Text(fontSize = 12.sp, text = reply.writeDate)
                    Text(fontSize = 12.sp, text = reply.boardEntity)
                    Text(fontSize = 12.sp, text = reply.companyEntity)
                    Text(fontSize = 12.sp, text = reply.tradeEntity)
                    Text(fontSize = 12.sp, text = reply.userId)
                    Text(fontSize = 12.sp, text = reply.writeId)
                    Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
                    Spacer(Modifier.size(10.dp))
                }
            }
        }
    } else {
        Text(text = "댓글 없당께롱")
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