package com.example.projecttravel.ui.screens.infome

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText

import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun MyHexaGraph(
//    userInfoUiState: UserInfoUiState.UserInfoSuccess,
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
) {

//    val data = listOf(8, 6, 9, 7, 5, 4) // 예제 데이터 포인트
//    val labels = listOf("A", "B", "C", "D", "E" , "F") // 축 레이블
//
//    RadarChart(data, labels)



//    LazyColumn(
//        verticalArrangement = Arrangement.spacedBy(24.dp),
//    ) {
//        items(
//            items = userInfoUiState.userInfoList,
//            key = { userInfo ->
//                userInfo.user
//            }
//        ) { userInfo ->
//            Column {
//                Text(text = "user = ${userInfo.user}")
//                Text(text = "id = ${userInfo.id}")
//                Text(text = "culture = ${userInfo.culture}")
//                Text(text = "food = ${userInfo.food}")
//                Text(text = "history = ${userInfo.history}")
//                Text(text = "nature = ${userInfo.nature}")
//                Text(text = "reliability = ${userInfo.reliability}")
//                Text(text = "religion = ${userInfo.religion}")
//                Text(text = "sights = ${userInfo.sights}")
//            }
//            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//        }
//    }
}

//@Composable
//fun RadarChart(
//    data: List<Int>, // Radar Chart의 데이터 포인트
//    labels: List<String> // 축 레이블
//) {
//    Canvas(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        val centerX = size.width / 2
//        val centerY = size.height / 2
//        val radius = min(centerX, centerY) * 0.8 // 필요에 따라 크기 조정
//
//        // 데이터 포인트 개수 계산
//        val numPoints = data.size
//        val angle = (2 * PI) / numPoints
//
//        // 축 및 데이터 라인 그리기
//        for (i in 0 until numPoints) {
//            val currentAngle = i * angle - (PI / 2)
//            val x = centerX + radius * cos(currentAngle)
//            val y = centerY + radius * sin(currentAngle)
//
//            // 축 그리기
//            drawLine(
//                brush = Brush.linearGradient(listOf(Color.Gray)),
//                start = Offset(centerX, centerY),
//                end = Offset(x.toFloat(), y.toFloat()),
//                strokeWidth = 2f
//            )
//
//            // 데이터 라인 그리기
//            val value = data[i] // 해당 포인트의 데이터 값 가져오기
//            val dataX = centerX + radius * value * cos(currentAngle)
//            val dataY = centerY + radius * value * sin(currentAngle)
//            drawLine(
//                brush = Brush.linearGradient(listOf(Color.Blue)),
//                start = Offset(centerX, centerY),
//                end = Offset(dataX.toFloat(), dataY.toFloat()),
//                strokeWidth = 2f
//            )
//        }
//
//        // 레이블 그리기
//        drawIntoCanvas { canvas ->
//            labels.forEachIndexed { index, label ->
//                val currentAngle = index * angle - (PI / 2)
//                val x = centerX + radius * cos(currentAngle)
//                val y = centerY + radius * sin(currentAngle)
//                drawContext.canvas.nativeCanvas.drawText(
//                    label, // 텍스트 내용
//                    x.toFloat(), // x 좌표
//                    y.toFloat(), // y 좌표
//                    Paint().apply { color = Color.Black.toArgb() }
//                )
//            }
//        }
//    }
//}
