package com.example.projecttravel.ui.screens.planroutegps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.SpotDto
import com.example.projecttravel.model.SpotDtoResponse
import com.example.projecttravel.model.SpotDtoResponseRead
import com.example.projecttravel.ui.screens.plantrip.PlanCardTourAttr
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import java.time.LocalDate

@Composable
fun RouteGpsPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onBackButtonClicked: () -> Unit = {},
) {
    val currentDayTripAttrs: SpotDtoResponse? =
        when (planUiState.checkSingleDayGps) {
            is SpotDtoResponseRead -> {
                val spotDtoResponse = SpotDtoResponse(
                    date = planUiState.checkSingleDayGps.date,
                    list = planUiState.checkSingleDayGps.list.map { spotDtoRead ->
                        SpotDto(
                            pk = spotDtoRead.pk,
                            name = spotDtoRead.name,
                            img = spotDtoRead.img,
                            lan = spotDtoRead.lan,
                            lat = spotDtoRead.lat,
                            inOut = spotDtoRead.inOut.toString(), // Int를 String으로 변환
                            cityId = spotDtoRead.cityId.toString() // Int를 String으로 변환
                        )
                    }
                )
                spotDtoResponse
            }
            is SpotDtoResponse -> planUiState.checkSingleDayGps
            else -> null
        }
    if (currentDayTripAttrs != null) {
        Column {
            Text(text = "고른 날짜 확인 = ${currentDayTripAttrs.date}")
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            Text(text = "당일 여행 확인 밑에")
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(
                    items = currentDayTripAttrs.list,
                    key = { userPlan -> userPlan.pk }
                ) {attrs ->

                    Text(text = "name = ${attrs.name}")
                    Text(text = "lan = ${attrs.lan}")
                    Text(text = "lat} = ${attrs.lat}")
                    Text(text = "cityId = ${attrs.cityId}")
                    Text(text = "inOut = ${attrs.inOut}")

                    Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
                }
            }

        }
    } else {
        Text(text = "오류 발생 , 뒤로가기 ㄱㄱ")
    }
}