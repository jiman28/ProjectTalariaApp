package com.example.projecttravel.ui.screens.boardview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.model.RemoveComment
import com.example.projecttravel.model.SendComment
import com.example.projecttravel.ui.screens.boardlist.boarddialogs.RemoveCommentDialog
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.ui.screens.boardlist.readapi.getReplyListMobile
import com.example.projecttravel.ui.screens.boardlist.readapi.sendCommentToDb
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun ViewReply(
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    userUiState: UserUiState,
    currentArticleNo: Int,
    onContentRefreshClicked: () -> Unit,
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

    val filteredReplyList = boardPageUiState.currentReplyList

    val tabtitle = stringResource(boardPageUiState.currentSelectedBoardTab)

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(fontSize = 25.sp, text = stringResource(R.string.replyTitle))
            Spacer(modifier = Modifier.padding(3.dp))
            Text(fontSize = 18.sp, text = "(${filteredReplyList?.size ?: 0})") // 크기를 출력
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Spacer(modifier = Modifier.padding(5.dp))
        if (filteredReplyList != null) {
            if (filteredReplyList.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                ) {
                    Text(fontSize = 15.sp, text = "댓글이 없네요\n첫 번째 댓글을 작성해주세요!")
                }
            } else {
                Column {
                    filteredReplyList.forEach { reply ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                        ) {
                            Column(
                                modifier = Modifier.weight(8f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Row {
                                        Icon(
                                            modifier = Modifier.size(15.dp),
                                            imageVector = Icons.Filled.AccountCircle,
                                            contentDescription = "Account"
                                        )
                                        Spacer(modifier = Modifier.padding(1.dp))
                                        Text(fontSize = 12.sp, text = reply.writeId)

                                        Spacer(modifier = Modifier.padding(5.dp))

                                        Icon(
                                            modifier = Modifier.size(15.dp),
                                            imageVector = Icons.Filled.AccessTime,
                                            contentDescription = "WriteDate"
                                        )
                                        Spacer(modifier = Modifier.padding(1.dp))
                                        Text(fontSize = 12.sp, text = reply.writeDate)
                                    }
                                    Row {
                                    }
                                }
                                Column {
                                    Text(
                                        modifier = Modifier.padding(start = 3.dp),
                                        fontSize = 15.sp,
                                        text = reply.replyContent
                                    )
                                }
                            }
                            //                    Column(
                            //                        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                            //                        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                            //                        modifier = Modifier.weight(1f)
                            //                    ) {
                            //                        Icon(
                            //                            modifier = Modifier.size(15.dp),
                            //                            imageVector = Icons.Filled.Edit,
                            //                            contentDescription = "EditComment"
                            //                        )
                            //                    }
                            Column(
                                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                                modifier = Modifier.weight(1f)
                            ) {
                                //                        userUiState.currentLogin?.id?.let { Text(text = "로그인 $it") }
                                //                        Text(text = "리플 ${reply.userId}")
                                var isRemoveCommentDialog by remember { mutableStateOf(false) }
                                if (userUiState.currentLogin?.id == reply.userId) {
                                    Icon(
                                        modifier = Modifier
                                            .size(15.dp)
                                            .clickable {
                                                isRemoveCommentDialog = true
                                            },
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = "CancelComment"
                                    )
                                }
                                if (isRemoveCommentDialog) {
                                    val removeComment = RemoveComment(
                                        tabTitle = tabtitle,
                                        articleNo = currentArticleNo.toString(),
                                        replyNo = reply.replyNo,
                                    )
                                    RemoveCommentDialog(
                                        removeComment = removeComment,
                                        currentArticleNo = currentArticleNo,
                                        tabtitle = tabtitle,
                                        boardPageViewModel = boardPageViewModel,
                                        onContentRefreshClicked = onContentRefreshClicked,
                                        onDismiss = {
                                            isRemoveCommentDialog = false
                                        },
                                        onLoadingStarted = {
                                            isLoadingState = true
                                        },
                                        onErrorOccurred = {
                                            isLoadingState = false
                                        },
                                    )
                                }
                            }
                        }
                        Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
                        Spacer(modifier = Modifier.padding(3.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Column {
            var txtErrorMsg by remember { mutableStateOf("") }
            var isTextErrorDialog by remember { mutableStateOf(false) }

            var commentContent by remember { mutableStateOf("") }
            var remainingCharacters by remember { mutableStateOf(100) } // 남은 문자 수를 나타내는 변수 추가

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(fontSize = 25.sp, text = "댓글 작성")
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.End, // 수평 가운데 정렬
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                if (commentContent == "") {
                                    txtErrorMsg = "댓글을 작성하세요!"
                                    isTextErrorDialog = true
                                } else {
                                    scope.launch {
                                        val sendComment = userUiState.currentLogin?.let {
                                            SendComment(
                                                tabTitle = tabtitle,
                                                articleNo = currentArticleNo.toString(),
                                                replyContent = commentContent,
                                                email = it.email,
                                            )
                                        }
                                        isLoadingState = true
                                        // 비동기 작업을 시작하고 결과(return)를 받아오기 위한 Deferred 객체를 생성합니다.
                                        if (sendComment != null) {
                                            val commentDeferred = async {
                                                sendCommentToDb(sendComment)
                                            }
                                            // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                            val isCommentComplete = commentDeferred.await()
                                            // 모든 작업이 완료되었을 때만 실행합니다.
                                            if (isCommentComplete) {
                                                val callReply = CallReply(
                                                    tabtitle = tabtitle,
                                                    articleNo = currentArticleNo.toString()
                                                )
                                                val isReplyDeferred =
                                                    async { getReplyListMobile(callReply) }
                                                // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                                val isReplyComplete = isReplyDeferred.await()
                                                if (isReplyComplete != null) {
                                                    isLoadingState = null
                                                    boardPageViewModel.setReplyList(isReplyComplete)
                                                    onContentRefreshClicked()
                                                } else {
                                                    isLoadingState = false
                                                }
                                            } else {
                                                isLoadingState = false
                                            }
                                        }
                                    }
                                }
                            },
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "EditComment"
                    )
                    if (isTextErrorDialog) {
                        TextMsgErrorDialog(
                            txtErrorMsg = txtErrorMsg,
                            onDismiss = {
                                isTextErrorDialog = false
                            },
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                    modifier = Modifier.weight(8f),
                ) {
                    val maxCharacters = 100
                    TextField(
                        value = commentContent,
                        onValueChange = {
                            // 문자 수 제한 로직 추가
                            if (it.length <= maxCharacters) {
                                commentContent = it
                                remainingCharacters = maxCharacters - it.length
                            }
                        },
                        label = { Text(text = "문자 수 제한: $remainingCharacters / 100") },
                        placeholder = { Text(text = "댓글을 작성해주세요.") },
                        trailingIcon = {  },
                        modifier = Modifier
                            .padding(16.dp)
                            .height(150.dp) // 높이 조절
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Default // 줄 바꿈 버튼 활성화
                        ),
                    )
                }
            }
        }
    }
}
