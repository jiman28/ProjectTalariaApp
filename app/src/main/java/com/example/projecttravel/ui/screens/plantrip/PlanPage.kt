package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan

@Composable
fun PlanPage(
    modifier: Modifier = Modifier,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onCancelButtonClicked: () -> Unit,  // 취소버튼 매개변수를 추가
    onNextButtonClicked: () -> Unit,
){

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
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            Column {
                Text(planUiState.planDateRange.toString())
            }
            /** ================================================== */
            /** Show your dateToWeather ====================*/
            Column {
                planUiState.dateToWeather.forEach { item ->
                    Row (
                    ) {
                        Text(
                            text = "${item.day}: ${item.inOut}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(3.dp) // 원하는 여백을 추가).
                        )
                        AsyncImage(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(item.icon)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.no_image_country),
                            placeholder = painterResource(id = R.drawable.loading_img)
                        )
                    }
                }
            }

            /** ================================================== */
            /** Show your dateToAttrByWeather ====================*/
            Column {
                planUiState.dateToAttrByWeather.forEach { (date, attractions) ->
                    Column {
                        // 날짜 표시
                        Text(
                            text = date,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(3.dp)
                        )

                        // 해당 날짜의 관광 목록 표시
                        attractions.forEach { attraction ->
                            Text(
                                text = "${date}: ${ attraction.name }", // 여기서 "name"은 실제 TourAttractionAll 모델의 속성 이름일 것입니다.
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(3.dp)
                            )
                        }
                    }
                }
            }

            /** ================================================== */
            /** Show your dateToSelectedTourAttractions ====================*/
            Column {
                planUiState.dateToSelectedTourAttractions.forEach { (date, attractions) ->
                    Column {
                        // 날짜 표시
                        Text(
                            text = date.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(3.dp)
                        )

                        // 해당 날짜의 관광 목록 표시
                        attractions.forEach { attraction ->
                            Text(
                                text = "${date}: ${
                                    when (attraction) {
                                        is TourAttractionInfo -> attraction.placeName
                                        is TourAttractionSearchInfo -> attraction.name
                                        else -> "몰루"
                                    }
                                }", // 여기서 "name"은 실제 TourAttractionAll 모델의 속성 이름일 것입니다.
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
