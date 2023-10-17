package com.example.projecttravel.ui.screens.searchPlaceGps

import android.location.Geocoder

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.SearchUiState
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.search.TourAttractionSearchInfo
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSearch
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttrSearchUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttrSearchViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val TAG1 = "AAAAA"
private const val TAG2 = "BBBBB"

@Composable
fun SearchGpsPage(
    modifier: Modifier = Modifier,
    selectUiState: SelectUiState,
    searchUiState: SearchUiState,
    searchViewModel: ViewModelSearch,
    selectViewModel: ViewModelSelect,
    onBackButtonClicked: () -> Unit = {},
    updateUiPageClicked: () -> Unit = {},
) {
    val tourAttrSearchViewModel: TourAttrSearchViewModel =
        viewModel(factory = TourAttrSearchViewModel.TourAttrSearchFactory)
    val tourAttrSearchUiState =
        (tourAttrSearchViewModel.tourAttrSearchUiState as? TourAttrSearchUiState.TourAttrSearchSuccess)
    if (tourAttrSearchUiState != null) {
        findSearchListByName(
            searchUiState.searchedPlace?.name,
            tourAttrSearchUiState.tourAttrSearchList
        )?.let { selectViewModel.setSearch(it) }
    }
    var stateInOut: String by remember { mutableStateOf("0") }
    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            /** Cancel DateRangeMenu Button ====================*/
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    selectViewModel.setSearch(null)
                    searchViewModel.resetAllSearchUiState()
                    onBackButtonClicked()
                }
            ) {
                Text(stringResource(R.string.close_button))
            }
            /** Reset DateRange Button ====================*/
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    selectViewModel.setSearch(null)
                    searchViewModel.resetAllSearchUiState()
                    updateUiPageClicked()
                }
            ) {
                Text(stringResource(R.string.reset_button))
            }
            /** Confirm DateRange Button  ====================*/
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    if (selectUiState.selectSearch != null) {
                        if (tourAttrSearchUiState != null) {
                            sendInOut(
                                placeName = searchUiState.searchedPlace?.name,
                                stateInOut = stateInOut
                            )
                            findSearchListByName(
                                searchUiState.searchedPlace?.name,
                                tourAttrSearchUiState.tourAttrSearchList
                            )?.let {
                                selectViewModel.addTourAttrSearch(
                                    it
                                )
                            }
                        }
                        selectViewModel.setSearch(null)
                        searchViewModel.resetAllSearchUiState()
                        onBackButtonClicked()
                    }
                }) {
                Text(stringResource(R.string.confirm_button))
            }

        }
        /** Searched Place */
        Column {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                when (searchUiState.errorMsg) {
                    "loading" -> Image(
                        painter = painterResource(R.drawable.loading_img),
                        contentDescription = stringResource(R.string.loading),
                        modifier = modifier.size(150.dp),
                    )

                    null -> {
                        if (selectUiState.selectSearch != null) {
                            SearchedTourAttr(selectUiState, stateInOut) { newInOut ->
                                stateInOut = newInOut
                            }
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
        /** GoogleMapSheet*/
        GoogleMapSheet(
            searchUiState = searchUiState,
            searchViewModel = searchViewModel,
            updateUiPageClicked = updateUiPageClicked,
        )
        /** SearchGpsDialog Screen closed when click phone's backButton */
        /** Must be placed inside 'if (isDatePickerVisible)' to apply this BackHandler logic to this Composable */
        BackHandler(
            onBack = onBackButtonClicked
        )
    }
}

/** Google API Service ====================*/
@Composable
fun GoogleMapSheet(
    searchUiState: SearchUiState,
    searchViewModel: ViewModelSearch,
    updateUiPageClicked: () -> Unit = {},
) {
    val locationViewModel: LocationViewModel = viewModel()
    val context = LocalContext.current

    /** Reset GOOGLE_MAPS_API_KEY ====================*/
    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
    Places.initialize(context, apiKey) // YOUR_API_KEY_HERE를 실제 API 키로 대체
    /** Reset placesClient & Geocoder ====================*/
    locationViewModel.placesClient = Places.createClient(context)
    locationViewModel.geoCoder = Geocoder(context)
    /** TextField on the Surface of GoogleMap Composable ====================*/
    Surface(
        modifier = Modifier
            .padding(8.dp),
//            .fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Bottom,
        ) {
            var text by remember { mutableStateOf("") }

            /** TextField for finding placeId = Place SDK ====================*/
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    locationViewModel.searchPlaces(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            )

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
                                    searchViewModel.setErrorMsg("loading")
                                    text = it.name
                                    locationViewModel.locationAutofill.clear()
                                    getPlaceInfo(
                                        it.placeId,
                                        context,
                                        searchViewModel,
                                        updateUiPageClicked,
                                    )
                                    // 여기다가 로딩창 뜨게 만들기
                                }
                        ) {
                            Text(it.name)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
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

/** Shows SearchedTourAttr ====================*/
@Composable
fun SearchedTourAttr(
    selectUiState: SelectUiState,
    stateInOut: String,
    onStateInOutChanged: (String) -> Unit,
) {
    Column (
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(selectUiState.selectSearch?.img)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.no_image_country),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = selectUiState.selectSearch?.name.toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
//            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
//        ) {
//            Text(
//                text = when (stateInOut) {
//                    "0" -> "실내(IN)입니다"
//                    else -> "실외(OUT)입니다"
//                },
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//                    .padding(dimensionResource(R.dimen.padding_medium)),
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Start
//            )
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            var stateRadioButton by remember { mutableStateOf(true) }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
            ) {
                RadioButton(
                    selected = stateRadioButton,
                    onClick = {
                        stateRadioButton = true
                        onStateInOutChanged("0")
                    },
                    modifier = Modifier.semantics { contentDescription = "In" }
                )
                Text(text = "In")
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
            ) {
                RadioButton(
                    selected = !stateRadioButton,
                    onClick = {
                        stateRadioButton = false
                        onStateInOutChanged("1")
                    },
                    modifier = Modifier.semantics { contentDescription = "Out" }
                )
                Text(text = "Out")
            }
        }
    }
}
