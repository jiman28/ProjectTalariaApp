package com.example.projecttravel.ui.screens.selection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.model.search.TourAttractionSearchInfo
import com.example.projecttravel.model.select.TourAttractionAll
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect

@Composable
fun SelectedTourAttractions(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect,
    contentPadding: PaddingValues,
) {
    if (selectUiState.selectTourAttractions.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
        ) {
            SelectedTourAttractionsMenu(
                selectUiState = selectUiState,
                selectViewModel = selectViewModel,
                contentPadding = contentPadding,
            )
            Text(
                text = extractLastName(selectUiState),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함 = 이게 있어야 TextAlign.Center 이 작동함
            )
        }
    }
}

@Composable
fun SelectedTourAttractionsMenu(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect,
    contentPadding: PaddingValues,
) {
    // 1. AlertDialog 펼쳐짐 상태 정의
    var isOpenTourAttrDialog by remember { mutableStateOf(false) }
    // 2. AlertDialog Open 상태를 변경하기 위한 버튼 정의
    Button(
        onClick = { isOpenTourAttrDialog = true }
    ) {
        Text(
            text = "선택한 관광지 확인"
        )
    }
    if (isOpenTourAttrDialog) {
        SelectedTourAttrDialog(
            selectUiState = selectUiState,
            selectViewModel = selectViewModel,
            contentPadding = contentPadding,
            onDismiss = { isOpenTourAttrDialog = false },
        )
    }
}

/** ===================================================================== */
/** SelectedTourAttrDialog to check SelectedTourAttr for trip ====================*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedTourAttrDialog(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect,
    contentPadding: PaddingValues,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(Color.DarkGray, RoundedCornerShape(12.dp))
    ) {
        LazyColumn(
            modifier = Modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                items = selectUiState.selectTourAttractions,
                key = { tourAttractionAll ->
                    when (tourAttractionAll) {
                        is TourAttractionInfo -> tourAttractionAll.placeName
                        is TourAttractionSearchInfo -> tourAttractionAll.name
                        else -> {}
                    }
                }
            ) { tourAttractionAll ->
                SelectedTourAttrCard(
                    tourAttractionAll = tourAttractionAll,
                    selectViewModel = selectViewModel,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
    BackHandler(
        onBack = { onDismiss() }
    )
}

/** ===================================================================== */
/** Shows the last selected TourAttr ====================*/
fun extractLastName(selectUiState: SelectUiState): String {
    return when (val lastAttraction = selectUiState.selectTourAttractions.lastOrNull()) {
        is TourAttractionInfo -> lastAttraction.placeName
        is TourAttractionSearchInfo -> lastAttraction.name
        else -> "관광지 골라 골라"
    }
}

/** ================================================== */
/** Each Selected Tour Attraction Info Cards UI */
@Composable
fun SelectedTourAttrCard(
    tourAttractionAll: TourAttractionAll,
    selectViewModel: ViewModelSelect,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            IconButton(
                onClick = { selectViewModel.cancelTourAttraction(tourAttractionAll) }
            ) {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "CancelTourAttraction")
            }
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(
                        when (tourAttractionAll) {
                            is TourAttractionInfo -> tourAttractionAll.imageP
                            is TourAttractionSearchInfo -> tourAttractionAll.img
                            else -> {}
                        }
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.no_image_country),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Text(
                text = when (tourAttractionAll) {
                    is TourAttractionInfo -> tourAttractionAll.placeName
                    is TourAttractionSearchInfo -> tourAttractionAll.name
                    else -> "몰루"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}
