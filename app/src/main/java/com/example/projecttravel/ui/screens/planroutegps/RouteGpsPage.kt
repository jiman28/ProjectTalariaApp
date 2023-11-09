package com.example.projecttravel.ui.screens.planroutegps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.SpotDto
import com.example.projecttravel.model.SpotDtoResponse
import com.example.projecttravel.model.SpotDtoResponseRead
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun RouteGpsPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    onBackButtonClicked: () -> Unit = {},
) {
    val context = LocalContext.current
//    /** Reset GOOGLE_MAPS_API_KEY ====================*/
//    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
//    Places.initialize(context, apiKey) // YOUR_API_KEY_HERE를 실제 API 키로 대체

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

    var showPlaceInfo by remember { mutableStateOf(false) }
    var selectedPlaceMarker by remember { mutableStateOf<SpotDto?>(null) }
    if (showPlaceInfo) {
        selectedPlaceMarker?.let {
            PlaceViewDialog(
                selectedPlaceMarker = it,
                onDismiss = { showPlaceInfo = false },
            )
        }
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
                    .padding(15.dp) // 원하는 여백을 추가(start = 15.dp, end = 15.dp, ...)
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                    modifier = Modifier
                        .padding(15.dp) // 원하는 여백을 추가(start = 15.dp, end = 15.dp, ...)
                        .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
                ) {
                    Text(
                        text = "마커를 클릭하여 관광지 정보 확인",
                        fontSize = 15.sp,   // font 의 크기
                        lineHeight = 15.sp, // 줄 간격 = fontSize 와 맞춰야 글이 겹치지 않는다
                        fontWeight = FontWeight.Bold,  // font 의 굵기
                        style = MaterialTheme.typography.titleMedium,  //font 의 스타일
                            textAlign = TextAlign.Center, // 텍스트 내용을 compose 가운데 정렬
                            modifier = Modifier
                                .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
                    )
                }
                /** GoogleMap Composable ====================*/
                Column {
                    /** GoogleMap Marker Position ====================*/
                    // 모든 마커 위치 리스트
                    val markerPositions = currentDayTripAttrs.list.map { attrs ->
                        stringToLatLng(attrs.lat, attrs.lan)
                    }
                    // 모든 마커 의 중심점 계산
                    val centerLat = markerPositions.map { it.latitude }.average()
                    val centerLng = markerPositions.map { it.longitude }.average()
                    val centerPosition = LatLng(centerLat, centerLng)

                    /** GoogleMap Camera Position - 카메라 위치 조정 ====================*/
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(centerPosition, 12f)
                    }
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                    ) {
                        currentDayTripAttrs.list.forEach { attrs ->
                            Marker(
                                state = MarkerState(position = stringToLatLng(attrs.lat, attrs.lan),),
                                alpha = 3.0f,
                                anchor = Offset(0.5f, 0.5f),
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE), // 마커 아이콘 설정
                                title = attrs.name,
                                snippet = when (attrs.inOut) {
                                    "0" -> "실내 활동"
                                    "1" -> "실외 활동"
                                    else -> "몰루"
                                },
                                onInfoWindowClick = {
                                    showPlaceInfo = true
                                    selectedPlaceMarker = attrs
                                },
                            )
                        }
                    }
                }
            }
        } else {
            Text(text = "당일에 선택한 관광지가 없습니다")
        }
    }
}

// 위도(Latitude)와 경도(Longitude) 값을 저장하는 LatLng 객체로 변환
fun stringToLatLng(lat: String, lng: String): LatLng {
    val latitude = lat.toDouble()
    val longitude = lng.toDouble()
    return LatLng(latitude, longitude)
}
