package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.select.TourAttractionAll
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.ui.screens.selection.selectapi.WeatherResponseGet
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import java.time.LocalDate

@Composable
fun PlanPage(
    modifier: Modifier = Modifier,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onCancelButtonClicked: () -> Unit,  // 취소버튼 매개변수를 추가
    onNextButtonClicked: () -> Unit,
) {

    var selectedPlanDate by remember { mutableStateOf<LocalDate?>(null) }
    val sortedDates = planUiState.dateToSelectedTourAttractions.keys.sorted()

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
        /** Show your All Selections ====================*/
        Column {
            LazyRow(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth()
                    .background(
                        Color.LightGray,
                        shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
                    )
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(sortedDates) { date ->
                    val weatherResponseGet = planUiState.dateToWeather.find { it.day == date.toString() }
                    if (weatherResponseGet != null) {
                        DateCard(
                            date = date,
                            weatherResponseGet = weatherResponseGet,
                            onClick = { selectedPlanDate = date } // Update selectedPlanDate
                        )
                    } else {
                        DateCard(
                            date = date,
                            weatherResponseGet = null,
                            onClick = { selectedPlanDate = date } // Update selectedPlanDate
                        )
                    }
                }
            }
            // You can display the selected date if needed
            selectedPlanDate?.let { date ->
                Text("Selected Date: $date")
            }
            if (selectedPlanDate != null) {
                val selectedLocalDate = selectedPlanDate
                val selectedDateAttrs = planUiState.dateToSelectedTourAttractions[selectedLocalDate] ?: emptyList()
                LazyColumn(
                    modifier = Modifier,
                    contentPadding = PaddingValues(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(selectedDateAttrs) { tourAttractionAll ->
                        PlanTourAttrCard(
                            tourAttractionAll = tourAttractionAll,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanTourAttrCard(
    modifier: Modifier = Modifier,
    tourAttractionAll: TourAttractionAll,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(
                        when (tourAttractionAll) {
                            is TourAttractionInfo -> tourAttractionAll.imageP
                            is TourAttractionSearchInfo -> tourAttractionAll.img
                            else -> {}
                        }
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.no_image_country),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = when (tourAttractionAll) {
                    is TourAttractionInfo -> tourAttractionAll.placeName
                    is TourAttractionSearchInfo -> tourAttractionAll.name
                    else -> "몰루"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun DateCard(
    date: LocalDate,
    weatherResponseGet: WeatherResponseGet?,
    onClick: (LocalDate) -> Unit, // 클릭 시 호출될 콜백 함수
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(date) // 클릭 시 해당 날짜를 콜백 함수로 전달
            },
    ) {
        // 이하 내용은 변경 없음
        Column(
            modifier = Modifier
                .padding(6.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .width(width = 120.dp)
                .fillMaxHeight(0.8f)
                .background(Color.White, RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 날짜 표시
            Text(
                text = date.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(3.dp)
            )
            if (weatherResponseGet != null) {
                AsyncImage(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(
                            weatherResponseGet.icon
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.no_image_country),
                    placeholder = painterResource(id = R.drawable.loading_img)
                )
            } else {
                Text(
                    text = "현재 데이터 없음 ㅈㅅ염",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
    }
}