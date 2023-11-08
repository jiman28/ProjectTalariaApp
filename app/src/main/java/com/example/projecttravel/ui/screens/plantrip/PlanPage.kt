package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.SpotDto
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect

@Composable
fun PlanPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    selectViewModel: ViewModelSelect,
    onCancelButtonClicked: () -> Unit,  // 취소버튼 매개변수를 추가
    onPlanCompleteClicked: () -> Unit,
    onRouteClicked: () -> Unit = {},
) {
    var selectedPlanDate by remember { mutableStateOf(planUiState.currentPlanDate) }
//    var weatherSwitchChecked by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
    ) {
        /** Buttons ====================*/
        Column {
            PlanPageButtons(
                userUiState = userUiState,
                userViewModel = userViewModel,
                planUiState = planUiState,
                planViewModel = planViewModel,
                selectViewModel = selectViewModel,
                onCancelButtonClicked = onCancelButtonClicked,
                onPlanCompleteClicked = onPlanCompleteClicked,
            )
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        /** ================================================== */
        /** Buttons change between Random & Weather ====================*/
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, // 좌우로 공간을 나눠줌
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherSwitchButton(
                planUiState = planUiState,
                planViewModel = planViewModel,
            )
        }

        /** ================================================== */
        /** Show your Date List ====================*/
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        ) {
            PlanDateList(
                planUiState = planUiState,
                planViewModel = planViewModel,
                onDateClick = { clickedDate -> selectedPlanDate = clickedDate }
            )
        }

        /** ================================================== */
        /** Selected Date Info ====================*/
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        ) {
            SelectedPlanDateInfo(
                planUiState = planUiState,
                onRouteClicked = onRouteClicked,
            )
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        }

        /** ================================================== */
        /** Show your All Selections ====================*/
        if (planUiState.weatherSwitch) {
            Column {
                val selectedDateWeather: List<SpotDto> = if (planUiState.weatherSwitch) {
                    val selectedDate = selectedPlanDate.toString()
                    val selectedDateWeather = planUiState.dateToAttrByWeather
                        .find { it.date == selectedDate }?.list ?: emptyList()
                    selectedDateWeather
                } else {
                    emptyList()
                }
                if (selectedDateWeather.isNotEmpty()) {
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
                } else {
                    NoPlanListFound()
                }
            }
        } else {
            Column {
                val selectedDateAttrs: List<SpotDto> = if (!planUiState.weatherSwitch) {
                    val selectedDate = selectedPlanDate.toString()
                    val selectedDateAttrs = planUiState.dateToAttrByCity
                        .find { it.date == selectedDate }?.list ?: emptyList()
                    selectedDateAttrs
                } else {
                    emptyList()
                }
                if (selectedDateAttrs.isNotEmpty()) {
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
                } else {
                    NoPlanListFound()
                }
            }
        }
    }
}

@Composable
fun NoPlanListFound (
) {
    Spacer(modifier = Modifier.padding(10.dp))
    Text(
        text = "목록이 없습니다.",
        fontSize = 25.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}