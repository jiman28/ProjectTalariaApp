package com.example.projecttravel.ui.screens.searchplace

import android.location.Geocoder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.SearchUiState
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.ui.screens.infome.infoapi.getPeopleLikeMe
import com.example.projecttravel.ui.screens.searchplace.searchapi.LocationViewModel
import com.example.projecttravel.ui.screens.searchplace.searchapi.getPlaceInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSearch
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/** Google API Service ====================*/
@Composable
fun GoogleMapSheet(
    searchedPlaceId: String,
    selectUiState: SelectUiState,
    searchUiState: SearchUiState,
    searchViewModel: ViewModelSearch,
    updateUiPageClicked: () -> Unit = {},
    onPlaceIdChanged: (String) -> Unit,
) {
    val locationViewModel: LocationViewModel = viewModel()
    val context = LocalContext.current

    /** Reset GOOGLE_MAPS_API_KEY ====================*/
    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
    Places.initialize(context, apiKey) // YOUR_API_KEY_HERE를 실제 API 키로 대체
    /** Reset placesClient & Geocoder ====================*/
    locationViewModel.placesClient = Places.createClient(context)
    locationViewModel.geoCoder = Geocoder(context)

    val scope = rememberCoroutineScope()

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    var errorMsg by remember { mutableStateOf("") }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> TextMsgErrorDialog(
                txtErrorMsg = errorMsg,
                onDismiss = { isLoadingState = null })

            else -> isLoadingState = null
        }
    }

    Column (
        modifier = Modifier.padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var text by remember { mutableStateOf("") }
        /** TextField for finding placeId = Place SDK ====================*/
        Row {
            Column (
                modifier = Modifier
                    .weight(5f)
                    .padding(3.dp),
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        locationViewModel.searchPlaces(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done // 완료 버튼 활성화
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                )
            }
            Column (
                modifier = Modifier
                    .weight(1.5f)
                    .padding(3.dp),
            ) {
                OutlinedButton(
                    onClick = {
//                        scope.launch {
//                            isLoadingState = true
//                            val peopleDeferred = async { getPeopleLikeMe(currentUserInfo) }
//                            val peopleComplete = peopleDeferred.await()
//                            if (peopleComplete.isNotEmpty()) {
//                                isLoadingState = null
//                                userViewModel.setLikeUsers(peopleComplete)
//                                onNextButtonClicked()
//                            } else {
//                                peopleErrorMsg = "서버 오류"
//                                isLoadingState = false
//                            }
//                        }







                        searchViewModel.setErrorMsg("loading")
                        getPlaceInfo(
                            searchedPlaceId,
                            searchUiState.inOutChecker,
                            context,
                            selectUiState,
                            searchViewModel,
                            updateUiPageClicked,
                        )
                    },
                    shape = RoundedCornerShape(0.dp),
                ) {
                    Text(text = "검색")
                }
            }
        }
        /** Visible when text anything ====================*/
        AnimatedVisibility(
            locationViewModel.locationAutofill.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            /** Touching the placeName to get placeId and use it to find placeInfos ====================*/
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(locationViewModel.locationAutofill) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                text = it.name
                                onPlaceIdChanged(it.placeId)
                                locationViewModel.locationAutofill.clear()
                            }
                    ) {
                        Text(text = it.name)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }

    /** View Searched Place */
    Column {
        Column(
            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        ) {
            when (searchUiState.errorMsg) {
                "loading" -> Image(
                    painter = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.loading),
                    modifier = Modifier.size(150.dp),
                )

                null -> {
                    if (selectUiState.selectSearch != null) {
                        SearchedTourAttrCard(selectUiState)
                    }
                }
                else -> Text(
                    text = searchUiState.errorMsg,
                    textAlign = TextAlign.Center,
                    lineHeight = 50.sp,    // 줄간 간격 = 왠만하면 fontSize 에 맞추도록 한다
                    fontSize = 50.sp,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
        }
    }

    /** GoogleMap Composable ====================*/
    Column {
        val currentLatLong = searchUiState.searchedPlace?.latLng
        if (currentLatLong != null) {
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
        } else {
            Text(
                text = "검색하면 지도가 나와용",
                textAlign = TextAlign.Center,
                lineHeight = 100.sp,    // 줄간 간격 = 왠만하면 fontSize 에 맞추도록 한다
                fontSize = 100.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
            )
        }
    }
}
