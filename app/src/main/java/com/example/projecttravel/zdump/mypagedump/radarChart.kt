package com.example.projecttravel.zdump.mypagedump

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadarChartDumpDump(
    dataPoints: List<Float>,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .aspectRatio(0.8f)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.width * 0.4f
        val sides = dataPoints.size
        val angle = 360 / sides
        val maxValue = 1f // 최대 값을 조정

        for (index in 0 until sides) {
            val startAngle = Math.toRadians((angle * index).toDouble())
            val endAngle = Math.toRadians((angle * (index + 1)).toDouble())
            val startValue = dataPoints[index].coerceIn(0f, maxValue)
            val endValue = dataPoints[(index + 1) % sides].coerceIn(0f, maxValue)

            val startX = centerX + (startValue * radius * cos(startAngle)).toFloat()
            val startY = centerY + (startValue * radius * sin(startAngle)).toFloat()

            val endX = centerX + (endValue * radius * cos(endAngle)).toFloat()
            val endY = centerY + (endValue * radius * sin(endAngle)).toFloat()

            drawLine(
                color = Color.Blue,
                start = Offset(centerX, centerY),
                end = Offset(startX, startY),
                strokeWidth = 2f
            )

            drawLine(
                color = Color.Blue,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2f
            )

            // Optional: Add labels at each point
            val labelX = centerX + (radius * 1.1f * cos(startAngle)).toFloat()
            val labelY = centerY + (radius * 1.1f * sin(startAngle)).toFloat()
            val labelText = "Label $index"
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    labelText,
                    labelX,
                    labelY,
                    Paint().apply {
                        color = Color.Black.toArgb()
                        textSize = 24f
                    }
                )
            }
        }
    }
}
