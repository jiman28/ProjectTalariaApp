package com.example.projecttravel.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** ===================================================================== */
/** ResetConfirmDialog to ask whether to reset or not ====================*/
@Composable
fun GlobalLoadingDialog(
) {
    AlertDialog(
        onDismissRequest = { },
        text = {
            Column (
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                Column {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(70.dp)
                            .padding(5.dp),
                        color = Color.Blue
                    )
                }
                Column {
                    Text(
                        text = "로딩 중입니다. 잠시만 기다려주세요.",
                        fontSize = 30.sp,
                        lineHeight = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {}
    )
}

/** ===================================================================== */
/** ResetConfirmDialog to ask whether to reset or not ====================*/
@Composable
fun GlobalErrorDialog(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        text = {
            Text(
                text = "오류가 발생했습니다\n불편을 드려 죄송합니다",
                fontSize = 30.sp,
                lineHeight = 30.sp,
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
}

/** ===================================================================== */
/** ResetConfirmDialog to ask whether to reset or not ====================*/
@Composable
fun TextMsgErrorDialog(
    txtErrorMsg: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        text = {
            Text(
                text = txtErrorMsg,
                fontSize = 30.sp,
                lineHeight = 30.sp,
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
}
