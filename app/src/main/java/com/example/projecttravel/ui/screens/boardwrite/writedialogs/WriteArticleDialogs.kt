package com.example.projecttravel.ui.screens.boardwrite.writedialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.SendArticle
import com.example.projecttravel.ui.screens.boardwrite.writeapi.sendArticleToDb
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.ui.screens.boardlist.readapi.getAllBoardDefault
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun ArticleConfirmDialog(
    sendArticle: SendArticle,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    onBackButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
    onLoadingStarted: () -> Unit,
    onErrorOccurred: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val callBoard = CallBoard(
        kw = boardPageUiState.currentSearchKeyWord,
        page = boardPageUiState.currentBoardPage,
        type = stringResource(boardPageUiState.currentSearchType),
        email = ""
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = "게시글을 저장하시겠습니까?",
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        scope.launch {
                            onLoadingStarted()
                            // 비동기 작업을 시작하고 결과(return)를 받아오기 위한 Deferred 객체를 생성합니다.
                            val articleDeferred = async { sendArticleToDb(sendArticle) }


                            // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                            val isArticleComplete = articleDeferred.await()
                            // 모든 작업이 완료되었을 때만 실행합니다.
                            if (isArticleComplete) {
                                val isDeferred = async { getAllBoardDefault(callBoard,boardPageViewModel,scope) }
                                val isComplete = isDeferred.await()
                                // 모든 작업이 완료되었을 때만 실행합니다.
                                if (isComplete) {
                                    boardPageViewModel.setWriteBoardMenu(R.string.selectTabTitle)
                                    onDismiss()
                                    onBackButtonClicked()
                                } else {
                                    onErrorOccurred()
                                }
                            } else {
                                onDismiss()
                                onErrorOccurred()
                            }
                        }
                    }
                ) {
                    Text(text = "확인", fontSize = 20.sp)
                }
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = "취소", fontSize = 20.sp)
                }
            }
        },
    )
}

@Composable
fun CancelWriteArticleDialog(
    boardPageViewModel: BoardPageViewModel,
    userViewModel: UserViewModel,
    onBackButtonClicked: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            userViewModel.setBackHandlerClick(false)
            onDismiss()
        },
        text = {
            Text(
                text = "작성을 취소하시겠습니까?\n지금까지 작성한 내용들은 저장되지 않습니다.",
                fontSize = 20.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        onDismiss()
                        boardPageViewModel.setWriteBoardMenu(R.string.selectTabTitle)
                        userViewModel.setBackHandlerClick(false)
                        onBackButtonClicked()
                    }
                ) {
                    Text(text = "확인", fontSize = 20.sp)
                }
                TextButton(
                    onClick = {
                        userViewModel.setBackHandlerClick(false)
                        onDismiss()
                    }
                ) {
                    Text(text = "취소", fontSize = 20.sp)
                }
            }
        },
    )
}
