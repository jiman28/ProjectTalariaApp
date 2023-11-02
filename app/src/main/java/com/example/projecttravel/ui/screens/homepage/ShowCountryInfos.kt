package com.example.projecttravel.ui.screens.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import com.example.projecttravel.model.CountryInfo
import com.example.projecttravel.ui.screens.viewmodels.homepage.HomepageUiState

@Composable
fun ShowCountryInfos(
    homepageUiState: HomepageUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    countryCardClicked: (CountryInfo) -> Unit
) {
    when (homepageUiState) {
        is HomepageUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is HomepageUiState.Success ->
            CountryListScreen(
                countryInfo = homepageUiState.countryInfo,
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                contentPadding = contentPadding,
                countryCardClicked = countryCardClicked // countryCardClicked 함수를 전달
            )

        else -> ErrorScreen(retryAction, modifier)
    }
}

@Composable
private fun CountryListScreen(
    countryInfo: List<CountryInfo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    countryCardClicked: (CountryInfo) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            items = countryInfo,
            key = { countryInfo ->
                countryInfo.countryName
            }
        ) { countryInfo ->
            TravelCard(countryInfo = countryInfo, modifier = Modifier.fillMaxSize()) {
                countryCardClicked(countryInfo)
            }
        }
    }
}

@Composable
fun TravelCard(
    countryInfo: CountryInfo,
    modifier: Modifier = Modifier,
    onClick: (CountryInfo) -> Unit,
) {
    var isCountryInfoDialogVisible by remember { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = modifier.clickable { onClick(countryInfo) }   // 카드 섹션을 버튼화
        ) {
            Text(
                text = countryInfo.countryName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        isCountryInfoDialogVisible = true
                    },
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(countryInfo.imageC)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.no_image_country),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                onClick = {
                    onClick(countryInfo)
                }
            ) {
                Text(text = "${countryInfo.countryName}로 여행 가즈아!!")
            }
        }
    }
    if (isCountryInfoDialogVisible) {
        CountryInfoDialog(
            countryInfo = countryInfo,
            onDismiss = {
                isCountryInfoDialogVisible = false
            }
        )
    }
}

/** The Pop-up Message Showing Country Information */
@Composable
fun CountryInfoDialog(
    countryInfo: CountryInfo,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column {
                Text(
                    text = countryInfo.countryName,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                    modifier = Modifier
                        .padding(10.dp) // 원하는 여백을 추가).
                        .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                )
                Text(
                    text = (stringResource(R.string.title_colon,
                        "사용 언어",
                        countryInfo.languageC)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = (stringResource(R.string.title_colon,
                        "사용 화폐",
                        countryInfo.currency)),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
                Text(
                    text = countryInfo.countryInfo,
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

/** The home screen displaying the loading message.*/
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

/** The home screen displaying error message with re-attempt button.*/
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}