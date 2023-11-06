package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan

@Composable
fun WeatherSwitchButton(
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
) {
    var weatherSwitchChecked by remember { mutableStateOf(planUiState.weatherSwitch) }
    // You can display the selected date if needed
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                if (weatherSwitchChecked) {
                    Text(
                        text = "날씨 모드 ON !!!",
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "날씨 모드 OFF",
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                Switch(
                    checked = weatherSwitchChecked,
                    onCheckedChange = { newCheckedState ->
                        weatherSwitchChecked = newCheckedState
                        if (newCheckedState) {
                            // 스위치가 켜졌을 때 실행할 작업
                            planViewModel.setWeatherSwitch(true)
                        } else {
                            // 스위치가 꺼졌을 때 실행할 작업
                            planViewModel.setWeatherSwitch(false)
                        }
                    },
                    thumbContent = if (weatherSwitchChecked) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Build,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }
    }
}
