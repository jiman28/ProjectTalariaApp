package com.example.projecttravel.ui.screens.boards

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
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
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.boards.boardapi.SendComment
import com.example.projecttravel.ui.screens.boards.boardapi.sendCommentToDb
import com.example.projecttravel.ui.screens.login.data.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.board.ReplyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListReply
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun ViewReply(
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    userUiState: UserUiState,
    currentArticleNo: String,
    onContentRefreshClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog( onDismissAlert = { isLoadingState = null } )
            false -> GlobalErrorDialog( onDismissAlert = { isLoadingState = null } )
            else -> isLoadingState = null
        }
    }

    val replyListViewModel: ViewModelListReply =
        viewModel(factory = ViewModelListReply.ReplyFactory)
    val replyUiState = (replyListViewModel.replyUiState as? ReplyUiState.ReplySuccess)

    val filteredReplyList = replyUiState?.replyList?.filter {
        when (boardSelectUiState.currentSelectedBoard) {
            R.string.board -> it.boardEntity == currentArticleNo
            R.string.trade -> it.tradeEntity == currentArticleNo
            R.string.company -> it.companyEntity == currentArticleNo
            else -> false
        }
    }

    val tabtitle: String = when (boardSelectUiState.currentSelectedBoard) {
        R.string.board -> stringResource(R.string.boardTitle)
        R.string.trade -> stringResource(R.string.tradeTitle)
        R.string.company -> stringResource(R.string.companyTitle)
        else -> ""
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(fontSize = 25.sp, text = stringResource(R.string.replyTitle))
            Spacer(modifier = Modifier.padding(3.dp))
            Text(fontSize = 18.sp, text = "(${filteredReplyList?.size ?: 0})") // 크기를 출력
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Column {
            filteredReplyList?.forEach { reply ->
                Row(
                    verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                    horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                ) {
                    Column(
//                        modifier = Modifier.weight(8f)
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
                            Text(fontSize = 15.sp, text = reply.replyContent)
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
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider1))
                Spacer(modifier = Modifier.padding(3.dp))
            }
        }
        Spacer(modifier = Modifier.padding(7.dp))
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        Column {

            var commentContent by remember { mutableStateOf("") }
            var remainingCharacters by remember { mutableStateOf(200) } // 남은 문자 수를 나타내는 변수 추가
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
                                scope.launch {
                                    val sendComment = userUiState.currentLogin?.let {
                                        SendComment(
                                            tabTitle = tabtitle,
                                            articleNo = currentArticleNo,
                                            replyContent = commentContent,
                                            email = it.email,
                                        )
                                    }
                                    Log.d("xxxx1xxxxxxxxxxxxxxxx", sendComment.toString())
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
                                            onContentRefreshClicked()
                                        } else {
                                            isLoadingState = false
                                        }
                                    }
                                }
                            },
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "EditComment"
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                    modifier = Modifier.weight(8f)
                ) {
                    val maxCharacters = 200
                    TextField(
                        value = commentContent,
                        onValueChange = {
                            // 문자 수 제한 로직 추가
                            if (it.length <= maxCharacters) {
                                commentContent = it
                                remainingCharacters = maxCharacters - it.length
                            }
                        },
                        label = { Text(text = "남은 문자 수: $remainingCharacters / 200") },
                        placeholder = { Text(text = "댓글을 작성해주세요.") },
                        trailingIcon = {

                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                    )
                }
            }
        }
    }
}