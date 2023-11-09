package com.example.projecttravel.zdump.testboard

//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.unit.dp
//import com.example.projecttravel.R
//import com.example.projecttravel.data.uistates.SelectUiState
//import com.example.projecttravel.data.viewmodels.ViewModelSelect
//
//private const val TAG = "AAAAA"
//
//@Composable
//fun TestBoardPage(
//    modifier: Modifier = Modifier,
//    selectUiState: SelectUiState,
//    selectViewModel: ViewModelSelect,
//    onCancelButtonClicked: () -> Unit,  // 취소버튼 매개변수를 추가
//    onNextButtonClicked: () -> Unit = {},
//) {
//    Column {
//        /** Buttons ====================*/
//        Column {
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                /** Cancel Button go to go back ====================*/
//                Button(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(3.dp),
//                    onClick = onCancelButtonClicked
//                ) {
//                    Text(stringResource(R.string.cancel_button))
//                }
//                /** just temporary button ====================*/
//                OutlinedButton(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(3.dp),
//                    onClick = { }
//                ) {
////                    Text(stringResource(R.string.reset_button))
//                    Text(text = "게시판")
//                }
//                /** Next Button to go forward ====================*/
//                Button(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(3.dp),
//                    onClick = onNextButtonClicked
//                ) {
//                    Text(stringResource(R.string.next_button))
//                }
//            }
//        }
//        Column {
//            var boardAName by remember { mutableStateOf("") }
//            var boardAContent by remember { mutableStateOf("") }
//            var boardAAuthor by remember { mutableStateOf("") }
//
//            TextField(
//                value = boardAName,
//                onValueChange = { newValue -> boardAName = newValue },
//                label = { Text(text = "게시판 이름") },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        onNextButtonClicked()
//                    }
//                )
//            )
//
//            TextField(
//                value = boardAContent,
//                onValueChange = { newValue -> boardAContent = newValue },
//                label = { Text(text = "게시판 내용") },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        // 키보드의 "완료" 버튼을 클릭했을 때 실행되는 로직을 작성합니다.
//                        // 예를 들어, 다음 필드로 이동하거나 요청을 보내는 등의 작업을 수행할 수 있습니다.
//                        onNextButtonClicked()
//                    }
//                )
//            )
//
//            TextField(
//                value = boardAAuthor,
//                onValueChange = { newValue -> boardAAuthor = newValue },
//                label = { Text(text = "게시판 작성자") },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = {
//                        // 키보드의 "완료" 버튼을 클릭했을 때 실행되는 로직을 작성합니다.
//                        // 예를 들어, 다음 필드로 이동하거나 요청을 보내는 등의 작업을 수행할 수 있습니다.
//                        onNextButtonClicked()
//                    }
//                )
//            )
//
//            // "게시판" 버튼 클릭 시 WriteText 함수 호출
//            OutlinedButton(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(3.dp),
//                onClick = {
//                    val testBoardASend = TestBoardASend(
//                        boardAName = boardAName,
//                        boardAContent = boardAContent,
//                        boardAAuthor = boardAAuthor
//                    )
////                    writeText(testBoardASend)
//                }
//            ) {
//                Text(text = "게시판")
//            }
//        }
//    }
//}
//
/////** HTTP Request & Response ====================*/
////fun writeText(testBoardASend: TestBoardASend) {
////    val call = RetrofitBuilderJson.travelJsonApiService.setTestBoardA(testBoardASend)
////    call.enqueue(
////        object : Callback<String> { // 비동기 방식 통신 메소드
////            override fun onResponse(
////                // 통신에 성공한 경우
////                call: Call<String>,
////                response: Response<String>,
////            ) {
////                if (response.isSuccessful) { // 응답 잘 받은 경우
////                    Log.d(TAG, response.body().toString())
////
////                } else {
////                    // 통신 성공 but 응답 실패
////                    Log.d(TAG, "FAILURE")
////                }
////            }
////            override fun onFailure(call: Call<String>, t: Throwable) {
////                // 통신에 실패한 경우
////                t.localizedMessage?.let { Log.d(TAG, it) }
////            }
////        }
////    )
////}
