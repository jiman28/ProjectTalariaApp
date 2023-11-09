package com.example.projecttravel.ui.screens.select

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.CityInfo
import com.example.projecttravel.model.CountryInfo
import com.example.projecttravel.model.InterestInfo
import com.example.projecttravel.model.TourAttractionInfo
import com.example.projecttravel.data.viewmodels.SelectViewModel
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttractionUiState

@Composable
fun ShowTourAttractionInfos(
    onGpsClicked: () -> Unit = {},
    selectUiState: SelectUiState,
    selectViewModel: SelectViewModel,
    tourAttractionUiState: TourAttractionUiState.TourAttractionSuccess,
    selectedCountry: CountryInfo?,
    selectedCity: CityInfo?,
    selectedInterest: InterestInfo?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    TourAttractionListScreen(
        onGpsClicked = onGpsClicked,
        selectUiState = selectUiState,
        selectViewModel = selectViewModel,
        tourAttractionInfo = tourAttractionUiState.tourAttractionList,
        selectedCountry = selectedCountry,
        selectedCity = selectedCity,
        selectedInterest = selectedInterest,
        contentPadding = contentPadding,
    )
}

@Composable
private fun TourAttractionListScreen(
    onGpsClicked: () -> Unit = {},
    selectUiState: SelectUiState,
    selectViewModel: SelectViewModel,
    tourAttractionInfo: List<TourAttractionInfo>,
    selectedCountry: CountryInfo?,
    selectedCity: CityInfo?,
    selectedInterest: InterestInfo?,
    contentPadding: PaddingValues,
) {
    if (selectedCountry != null && selectedCity != null && selectedInterest == null) {
        val filteredTourAttractionInfo = tourAttractionInfo.filter {
            it.cityId == selectedCity.cityId
        }
        TourAttrScreen(
            onGpsClicked = onGpsClicked,
            selectUiState = selectUiState,
            selectViewModel = selectViewModel,
            filteredTourAttractionInfo = filteredTourAttractionInfo,
            contentPadding = contentPadding,
        )
    }
    else if (selectedCountry != null && selectedCity != null && selectedInterest != null) {
        val filteredTourAttractionInfo = tourAttractionInfo.filter {
            it.cityId == selectedCity.cityId && it.interestId == selectedInterest.interestId
        }
        TourAttrScreen(
            onGpsClicked = onGpsClicked,
            selectUiState = selectUiState,
            selectViewModel = selectViewModel,
            filteredTourAttractionInfo = filteredTourAttractionInfo,
            contentPadding = contentPadding,
        )
    }
    else {
        Text(
            text = "여행할 나라와 도시를 선택하세요",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize() // 최대 크기로 설정
                .padding(16.dp) // 원하는 여백을 추가
        )
    }
}

/** ================================================== */
/** Tour Attraction Info Screen */
@Composable
fun TourAttrScreen(
    onGpsClicked: () -> Unit = {},
    selectUiState: SelectUiState,
    selectViewModel: SelectViewModel,
    filteredTourAttractionInfo: List<TourAttractionInfo>,
    contentPadding: PaddingValues,
) {
    Column {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            onClick = onGpsClicked
        ) {
            Text(text = "다른 관광지 검색하기")
        }
    }
    LazyColumn(
        modifier = Modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = filteredTourAttractionInfo.filter { it !in selectUiState.selectTourAttractions},    // 이미 고른 관광지는 안나오게 함
            key = { tourAttractionInfo ->
                tourAttractionInfo.placeName
            }
        ) { tourAttractionInfo ->
            TourAttrCard(tourAttractionInfo = tourAttractionInfo, modifier = Modifier.fillMaxSize()){
                selectViewModel.addTourAttraction(tourAttractionInfo)
            }
        }
    }
}

/** ================================================== */
/** Each Tour Attraction Info Cards UI */
@Composable
fun TourAttrCard(
    tourAttractionInfo: TourAttractionInfo,
    modifier: Modifier = Modifier,
    onClick: (TourAttractionInfo) -> Unit,
) {
    var isTourAttrInfoDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = modifier.clickable { onClick(tourAttractionInfo) }   // 카드 섹션을 버튼화
        ) {
            Text(
                text = tourAttractionInfo.placeName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            isTourAttrInfoDialogVisible = true
                        },
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(tourAttractionInfo.imageP)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.no_image_country),
                    placeholder = painterResource(id = R.drawable.loading_img)
                )
                IconButton(
                    modifier = Modifier
                        .padding(3.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.Blue)
                        .align(Alignment.BottomEnd),
                    onClick = { onClick(tourAttractionInfo) }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "CancelTourAttraction", tint = Color.White)
                }
//                OutlinedButton(
//                    modifier = Modifier
//                        .padding(3.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .align(Alignment.BottomEnd),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White),
//                    onClick = {
//                        onClick(tourAttractionInfo)
//                    }
//                ) {
//                    Text(text = "여기 갈랭")
//                }
            }
        }
    }
    if (isTourAttrInfoDialogVisible) {
        TourAttrInfoDialog(
            tourAttractionInfo = tourAttractionInfo,
            onDismiss = {
                isTourAttrInfoDialogVisible = false
            }
        )
    }
}

/** The Pop-up Message Showing Country Information */
@Composable
fun TourAttrInfoDialog(
    tourAttractionInfo: TourAttractionInfo,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column {
                Text(
                    text = tourAttractionInfo.placeName,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                    modifier = Modifier
                        .padding(10.dp) // 원하는 여백을 추가).
                        .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                )
                Text(
                    text = (stringResource(R.string.title_colon,
                        "입장 가격",
                        tourAttractionInfo.price)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = (stringResource(R.string.title_colon,
                        "특징",
                        tourAttractionInfo.placeType)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = (stringResource(R.string.title_colon,
                        "위치",
                        tourAttractionInfo.placeAddress,)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "확인",fontSize = 20.sp,)
            }
        },
    )
}
