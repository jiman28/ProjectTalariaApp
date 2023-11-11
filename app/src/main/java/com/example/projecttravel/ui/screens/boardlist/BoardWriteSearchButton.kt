package com.example.projecttravel.ui.screens.boardlist

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.boardlist.readapi.getAllBoardDefault
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun BoardWriteSearchButton(
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    onWriteButtonClicked: () -> Unit,
    onResetButtonClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var searchKeyWord by remember { mutableStateOf(boardPageUiState.currentSearchKeyWord) }
    val selectedType = boardPageUiState.currentSearchType

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                onClick = { onWriteButtonClicked() },
                shape = RoundedCornerShape(0.dp),
            ) {
                Text(text = "게시글 작성")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
            modifier = Modifier.fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
        ) {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
//            modifier = Modifier
//                .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
            ) {
                val scrollStateText = rememberScrollState()

                TextField(
                    value = searchKeyWord,
                    onValueChange = { newValue -> searchKeyWord = newValue },
                    label = { Text(text = "제목이나 내용 검색") },
                    maxLines = 1,
                    modifier = Modifier
                        .padding(3.dp)
                        .horizontalScroll(scrollStateText)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done // 완료 버튼 활성화
                    ),
                )
            }
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
//            modifier = Modifier
//                .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(1.dp),
                    onClick = {
                        scope.launch {
//                            isLoadingState = true
//                            val callBoard = CallBoard(kw = searchKeyWord, page = 0, type = selectedType, email = "")
//                            val isDeferred =
//                                async { getAllBoardDefault(callBoard, boardPageViewModel, scope) }
//                            val isComplete = isDeferred.await()
//                            // 모든 작업이 완료되었을 때만 실행합니다.
//                            if (isComplete) {
//                                isLoadingState = null
//                                boardPageViewModel.setSearchKeyWord(searchKeyWord)
//                                onResetButtonClicked()
//                            } else {
//                                isLoadingState = false
//                            }
                        }
                    },
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Text(
                        text = "검색",
                        fontSize = 25.sp,   // font 의 크기
                        lineHeight = 25.sp, // 줄 간격 = fontSize 와 맞춰야 글이 겹치지 않는다
                        fontWeight = FontWeight.ExtraBold,  // font 의 굵기
                        style = MaterialTheme.typography.titleLarge,  //font 의 스타일
                        textAlign = TextAlign.Center, // 텍스트 내용을 compose 가운데 정렬)
                    )
                }
            }
        }
    }
}
