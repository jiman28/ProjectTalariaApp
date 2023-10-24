package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.selection.selectapi.WeatherResponseGet
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import java.time.LocalDate

@Composable
fun PlanCardDate(
    date: LocalDate,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    weatherResponseGet: WeatherResponseGet?,
    onClick: (LocalDate) -> Unit, // 클릭 시 호출될 콜백 함수
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(date) // 클릭 시 해당 날짜를 콜백 함수로 전달
                planViewModel.setCurrentPlanDate(date)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .width(width = 120.dp)
                .height(200.dp)
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
                    text = "현재 데이터 없음",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(3.dp)
                )
            }
        }
    }
}
