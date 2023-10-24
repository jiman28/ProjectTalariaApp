package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.selection.selectapi.SpotDto
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import java.time.LocalDate

@Composable
fun PlanPage(
    modifier: Modifier = Modifier,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onCancelButtonClicked: () -> Unit,  // 취소버튼 매개변수를 추가
    onNextButtonClicked: () -> Unit,
    onRouteClicked: () -> Unit = {},
) {

    var selectedPlanDate by remember { mutableStateOf(planUiState.currentPlanDate) }
    var weatherSwitchChecked by remember { mutableStateOf(false) }
    Column {
        /** Buttons ====================*/
        Column {
            PlanPageButtons(
                planViewModel = planViewModel,
                onCancelButtonClicked = onCancelButtonClicked,
                onNextButtonClicked = onNextButtonClicked,
            )
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        /** ================================================== */
        /** Buttons change between Random & Weather ====================*/
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, // 좌우로 공간을 나눠줌
            verticalAlignment = Alignment.CenterVertically
        ) {
            // You can display the selected date if needed
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                if (weatherSwitchChecked) {
                    Text(text = "날씨모드 ON !!!")
                } else {
                    Text(text = "여기를 누르면 날씨모드로 변함 ->")
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.End, // 수평 가운데 정렬
            ) {
                Switch(
                    checked = weatherSwitchChecked,
                    onCheckedChange = {
                        weatherSwitchChecked = it
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

        /** ================================================== */
        /** Show your All Selections ====================*/
        if (weatherSwitchChecked) {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                DateList(
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    onDateClick = { clickedDate ->
                        selectedPlanDate = clickedDate
                    })
            }

            Text(text = selectedPlanDate.toString())
            Text(text = planUiState.currentPlanDate.toString())

            Column {
                OutlinedButton(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    onClick = onRouteClicked
                ) {
                    Text(text = "지도 보장")
                }
            }
            Column {
                val selectedDateWeather: List<SpotDto> = if (weatherSwitchChecked) {
                    val selectedDate = selectedPlanDate.toString()
                    val selectedDateWeather = planUiState.dateToAttrByWeather
                        .find { it.date == selectedDate }?.list ?: emptyList()
                    selectedDateWeather
                } else {
                    emptyList()
                }
                Box {
                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(selectedDateWeather) { spotDto ->
                            PlanCardTourWeather(
                                planUiState = planUiState,
                                planViewModel = planViewModel,
                                spotDto = spotDto,
                                modifier = Modifier.fillMaxSize(),
                                onDateClick = { clickedDate ->
                                    selectedPlanDate = clickedDate // 여기서 selectedPlanDate 변경
                                }
                            )
                        }
                    }

                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                DateList(
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    onDateClick = { clickedDate ->
                        selectedPlanDate = clickedDate
                    }
                )
            }

            Text(text = selectedPlanDate.toString())
            Text(text = planUiState.currentPlanDate.toString())

            Column {
                OutlinedButton(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    onClick = onRouteClicked
                ) {
                    Text(text = "지도 보장")
                }
            }
            Column {
                val selectedDateAttrs: List<SpotDto> = if (weatherSwitchChecked == false) {
                    val selectedDate = selectedPlanDate.toString()
                    val selectedDateAttrs = planUiState.dateToAttrByRandom
                        .find { it.date == selectedDate }?.list ?: emptyList()
                    selectedDateAttrs
                } else {
                    emptyList()
                }
                LazyColumn(
                    modifier = Modifier,
                    contentPadding = PaddingValues(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(selectedDateAttrs) { spotDto ->
                        PlanCardTourAttr(
                            planUiState = planUiState,
                            planViewModel = planViewModel,
                            spotDto = spotDto,
                            modifier = Modifier.fillMaxSize(),
                            onDateClick = { clickedDate ->
                                selectedPlanDate = clickedDate // 여기서 selectedPlanDate 변경
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DateList(
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onDateClick: (LocalDate) -> Unit,
) {
    val sortedDates = planUiState.dateToSelectedTourAttrMap.keys.sorted()
    LazyRow(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(
                Color.LightGray,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(sortedDates) { date ->
            val weatherResponseGet =
                planUiState.dateToWeather.find { it.day == date.toString() }
            if (weatherResponseGet != null) {
                PlanCardDate(
                    date = date,
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    weatherResponseGet = weatherResponseGet,
                    onClick = { onDateClick(date) } // Update selectedPlanDate
                )
            } else {
                PlanCardDate(
                    date = date,
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    weatherResponseGet = null,
                    onClick = { onDateClick(date) } // Update selectedPlanDate
                )
            }
        }
    }
}