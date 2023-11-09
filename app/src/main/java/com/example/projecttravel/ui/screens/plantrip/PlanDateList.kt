package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.SpotDtoResponse
import com.example.projecttravel.data.viewmodels.PlanViewModel
import java.time.LocalDate

@Composable
fun PlanDateList(
    allAttrList: List<SpotDtoResponse>,
    planUiState: PlanUiState,
    planViewModel: PlanViewModel,
    onDateClick: (LocalDate) -> Unit,
) {
    val sortedDates = planUiState.dateToSelectedTourAttrMap.keys.sorted()
    LazyRow(
        modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .background(
                Color.LightGray,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        items(sortedDates) { date ->
            val weatherResponseGet = planUiState.dateToWeather.find { it.day == date.toString() }
            val allAttrListByDate = allAttrList.find { it.date == date.toString() }
            val allAttrListSize = allAttrListByDate?.list?.size

            if (allAttrListSize != null) {
                if (weatherResponseGet != null) {
                    PlanCardDate(
                        date = date,
                        size = allAttrListSize,
                        planViewModel = planViewModel,
                        weatherResponseGet = weatherResponseGet,
                        onClick = { onDateClick(date) } // Update selectedPlanDate
                    )
                } else {
                    PlanCardDate(
                        date = date,
                        size = allAttrListSize,
                        planViewModel = planViewModel,
                        weatherResponseGet = null,
                        onClick = { onDateClick(date) } // Update selectedPlanDate
                    )
                }
            }
        }
    }
}