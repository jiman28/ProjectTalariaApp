package com.example.projecttravel.ui.screens.selection.selectdialogs

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.ui.screens.selection.selectapi.getDateToAttrByWeather
import com.example.projecttravel.ui.screens.selection.selectapi.getDateToWeather
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/** ===================================================================== */
/** PlanConfirmDialog to ask whether to go planPage or not ====================*/
@Composable
fun PlanConfirmDialog(
    planViewModel: ViewModelPlan,
    selectUiState: SelectUiState,
    onNextButtonClicked: () -> Unit = {},
    onDismiss: () -> Unit,
    onLoadingStarted: () -> Unit,
    onErrorOccurred: () -> Unit
) {

    val scope = rememberCoroutineScope()
    if (selectUiState.selectDateRange == null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = "날짜 고르삼",
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
                        }
                    ) {
                        Text(text = "확인", fontSize = 20.sp)
                    }
                }
            },
        )
    } else if (selectUiState.selectTourAttractions.isEmpty()) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = "관광지 고르삼",
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
                        }
                    ) {
                        Text(text = "확인", fontSize = 20.sp)
                    }
                }
            },
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = "선택사항들로 계획표를 만듭니다",
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
                                val weatherDeferred = async { getDateToWeather(selectUiState, planViewModel) }
                                val attrDeferred = async { getDateToAttrByWeather(selectUiState, planViewModel) }

                                // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                val isWeatherComplete = weatherDeferred.await()
                                val isAttrComplete = attrDeferred.await()

                                // 모든 작업이 완료되었을 때만 실행합니다.
                                if (isWeatherComplete && isAttrComplete) {
//                                if (isWeatherComplete) {
                                    planViewModel.setPlanDateRange(selectUiState.selectDateRange)
                                    planViewModel.setPlanTourAttr(selectUiState.selectTourAttractions)
                                    onDismiss()
                                    onNextButtonClicked()
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
}
