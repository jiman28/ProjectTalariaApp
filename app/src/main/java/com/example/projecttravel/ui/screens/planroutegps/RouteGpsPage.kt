package com.example.projecttravel.ui.screens.planroutegps

import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.SpotDto
import com.example.projecttravel.model.SpotDtoResponse
import com.example.projecttravel.model.SpotDtoResponseRead
import com.example.projecttravel.ui.screens.plantrip.PlanCardTourAttr
import com.example.projecttravel.ui.screens.searchplace.searchapi.LocationViewModel
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.time.LocalDate

@Composable
fun RouteGpsPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onBackButtonClicked: () -> Unit = {},
) {
    val context = LocalContext.current
    /** Reset GOOGLE_MAPS_API_KEY ====================*/
    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
    Places.initialize(context, apiKey) // YOUR_API_KEY_HERE를 실제 API 키로 대체
//    /** Reset placesClient & Geocoder ====================*/
//    val locationViewModel: LocationViewModel = viewModel()
//    locationViewModel.placesClient = Places.createClient(context)
//    locationViewModel.geoCoder = Geocoder(context)

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

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(10.dp) // 원하는 여백을 추가(start = 15.dp, end = 15.dp, ...)
            .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
    ) {
        if (currentDayTripAttrs != null) {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가(start = 15.dp, end = 15.dp, ...)
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
            ) {

                /** GoogleMap Composable ====================*/
                Column {
                    val firstAttr = currentDayTripAttrs.list[0]
                    val currentLatLong = stringToLatLng(firstAttr.lat , firstAttr.lan)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(currentLatLong, 15f)
                    }
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                    ) {
                        Marker(
                            state = MarkerState(position = currentLatLong),
                        )
                    }
                }
            }
        } else {
            Text(text = "당일에 선택한 관광지가 없습니다")
        }
    }

//    Column {
//        Text(text = "고른 날짜 확인 = ${currentDayTripAttrs.date}")
//        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//        Text(text = "당일 여행 확인 밑에")
//        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//        LazyColumn(
//            modifier = Modifier,
//            contentPadding = PaddingValues(5.dp),
//            verticalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            items(
//                items = currentDayTripAttrs.list,
//                key = { userPlan -> userPlan.pk }
//            ) { attrs ->
//
//                Text(text = "name = ${attrs.name}")
//                Text(text = "lan = ${attrs.lan}")
//                Text(text = "lat} = ${attrs.lat}")
//                Text(text = "cityId = ${attrs.cityId}")
//                Text(text = "inOut = ${attrs.inOut}")
//
//                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//            }
//        }
//
//    }
}

// 위도(Latitude)와 경도(Longitude) 값을 저장하는 LatLng 객체로 변환
fun stringToLatLng(lat: String, lng: String): LatLng {
    val latitude = lat.toDouble()
    val longitude = lng.toDouble()
    return LatLng(latitude, longitude)
}