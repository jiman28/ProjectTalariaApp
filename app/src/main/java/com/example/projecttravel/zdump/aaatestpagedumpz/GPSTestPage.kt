package com.example.projecttravel.zdump.aaatestpagedumpz

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.projecttravel.MainActivity
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.uistates.viewmodels.PlanViewModel
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.boardlist.readapi.getReplyListMobile
import com.example.projecttravel.ui.screens.boardlist.BoardsPageTabButtons
import com.example.projecttravel.ui.screens.planroutegps.stringToLatLng
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun GPSTestPage(
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    planUiState: PlanUiState,
    planViewModel: PlanViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    navController: NavHostController,
    scope: CoroutineScope,
) {
    val context = LocalContext.current
//    /** Reset GOOGLE_MAPS_API_KEY ====================*/
//    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
//    Places.initialize(context, apiKey) // YOUR_API_KEY_HERE를 실제 API 키로 대체

//    /** Reset placesClient & Geocoder ====================*/
//    val locationViewModel: LocationViewModel = viewModel()
//    locationViewModel.placesClient = Places.createClient(context)
//    locationViewModel.geoCoder = Geocoder(context)

    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }

    var lastKnownLocation by remember {
        mutableStateOf<Location?>(null)
    }

    var deviceLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
//    }

    val locationPermissionGranted =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

//    if (!locationPermissionGranted) {
//        // TODO: 위치 권한이 허용되지 않은 경우 권한을 요청하도록 구현하세요.
//        // ActivityCompat.requestPermissions를 사용하여 권한을 요청할 수 있습니다.
//        // 요청 결과는 onRequestPermissionsResult에서 처리해야 합니다.
//        return
//    }

    val locationResult = fusedLocationProviderClient.lastLocation
    locationResult.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            // 위치 가져오기 성공
            lastKnownLocation = task.result
            deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
//            cameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
            Log.d("TAG", "현재 위치: $deviceLatLng")
        } else {
            // 위치 가져오기 실패
            Log.e("TAG", "현재 위치를 가져오는 중 오류 발생", task.exception)
            Log.d("TAG", "현재 위치를 가져오는 중 오류 발생: ${task.exception?.message}")
            // 오류 메시지를 사용자에게 표시하거나 기본값을 사용하도록 처리할 수 있습니다.
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(stringToLatLng("37.5327756", "126.9830806"), 12f)
    }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier
            .padding(10.dp) // 원하는 여백을 추가(start = 15.dp, end = 15.dp, ...)
            .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 (정렬할 때 중요하게 작용)
    ) {
        /** GoogleMap Composable ====================*/
        Column {
            /** GoogleMap Marker Position ====================*/
            GoogleMap(
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = locationPermissionGranted)
            ) {
//                if (locationPermissionGranted) {
//                    MarkerInfoWindowContent(
//                        state = MarkerState(
//                            position = deviceLatLng
//                        )
//                    ) { marker ->
//                        Text(marker.title ?: "You", color = Color.Red)
//                    }
//                }
                Marker(
                    state = MarkerState(position = stringToLatLng("37.5327756", "126.9830806")),
                    alpha = 3.0f,
                    anchor = Offset(0.5f, 0.5f),
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE), // 마커 아이콘 설정
                )
            }


//            /** GoogleMap Camera Position - 카메라 위치 조정 ====================*/
//            val cameraPositionState = rememberCameraPositionState {
//                position = CameraPosition.fromLatLngZoom(stringToLatLng("37.5327756", "126.9830806"), 12f)
//            }
//            GoogleMap(
//                modifier = Modifier.fillMaxSize(),
//                cameraPositionState = cameraPositionState,
//            ) {
//                Marker(
//                    state = MarkerState(position = stringToLatLng("37.5327756", "126.9830806"),),
//                    alpha = 3.0f,
//                    anchor = Offset(0.5f, 0.5f),
//                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE), // 마커 아이콘 설정
//                )
//            }
        }
    }


}