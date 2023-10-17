package com.example.projecttravel.ui.screens.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.search.TourAttractionSearchInfo
import com.example.projecttravel.model.select.CountryInfo
import com.example.projecttravel.ui.screens.viewmodels.selection.CountryUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.CountryViewModel
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect
import com.example.projecttravel.model.select.CityInfo
import com.example.projecttravel.model.select.InterestInfo
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.selection.CityUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.CityViewModel
import com.example.projecttravel.ui.screens.viewmodels.selection.InterestUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.InterestViewModel
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttractionUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttractionViewModel

private const val TAG = "AAAAA"

@Composable
fun SelectPage(
    modifier: Modifier = Modifier,
    planViewModel: ViewModelPlan,
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    onGpsClicked: () -> Unit = {},
) {
    val countryViewModel: CountryViewModel = viewModel(factory = CountryViewModel.CountryFactory)
    val cityViewModel: CityViewModel = viewModel(factory = CityViewModel.CityFactory)
    val interestViewModel: InterestViewModel = viewModel(factory = InterestViewModel.InterestFactory)
    val tourAttractionViewModel: TourAttractionViewModel = viewModel(factory = TourAttractionViewModel.TourAttractionFactory)

    val selectedCountry by remember { mutableStateOf<CountryInfo?>(null) }
    val selectedCity by remember { mutableStateOf<CityInfo?>(null) }
    val selectedInterest by remember { mutableStateOf<InterestInfo?>(null) }

    var isResetDialogVisible by remember { mutableStateOf(false) }
    var isPlanConfirmVisible by remember { mutableStateOf(false) }

    Column(
    ) {
        /** Buttons ====================*/
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                /** Cancel Button go to go back ====================*/
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel_button))
                }
                /** Reset all selected options ====================*/
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    onClick = {
                        isResetDialogVisible = true
                    }
                ) {
                    Text(stringResource(R.string.reset_button))
                    if (isResetDialogVisible) {
                        ResetConfirmDialog(
                            selectViewModel = selectViewModel,
                            onDismiss = {
                                isResetDialogVisible = false
                            }
                        )
                    }
                }
                /** Next Button to go forward ====================*/
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    onClick = {
                        isPlanConfirmVisible = true
                    }
                ) {
                    Text(stringResource(R.string.next_button))
                    if (isPlanConfirmVisible) {
                        PlanConfirmDialog(
                            planViewModel = planViewModel,
                            selectUiState = selectUiState,
                            onNextButtonClicked = onNextButtonClicked,
                            onDismiss = {
                                isPlanConfirmVisible = false
                            }
                        )
                    }
                }
            }
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        /** Selections ====================*/
        Column {
            Column {
                SelectDateRange(
                    selectUiState = selectUiState,
                    selectViewModel = selectViewModel,
                )
            }
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                /** ================================================== */
                /** Country Selection */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                ) {
                    val countryUiState =
                        (countryViewModel.countryUiState as? CountryUiState.CountrySuccess)
                    if (countryUiState != null) {
                        SelectCountry(
                            selectUiState = selectUiState,
                            countryUiState = countryUiState,
                            selectViewModel = selectViewModel,
                            selectedCountry = selectedCountry,
                        )
                    }
                }
                /** ================================================== */
                /** City Selection */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                ) {
                    val cityUiState =
                        (cityViewModel.cityUiState as? CityUiState.CitySuccess)
                    if (cityUiState != null) {
                        SelectCity(
                            selectUiState = selectUiState,
                            cityUiState = cityUiState,
                            selectViewModel = selectViewModel,
                            selectedCountry = selectUiState.selectCountry,
                            selectedCity = selectedCity,
                        )
                    }
                }
                /** ================================================== */
                /** Interest Selection */
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(3.dp),
                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                ) {
                    val interestUiState =
                        (interestViewModel.interestUiState as? InterestUiState.InterestSuccess)
                    if (interestUiState != null) {
                        SelectInterest(
                            selectUiState = selectUiState,
                            interestUiState = interestUiState,
                            selectViewModel = selectViewModel,
                            selectedCountry = selectUiState.selectCountry,
                            selectedCity = selectUiState.selectCity,
                            selectedInterest = selectedInterest,
                        )
                    }
                }
                /** ================================================== */
            }
        }
        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        /** ================================================== */
        /** Show your Selection ====================*/
        Column(
        ) {
            SelectedTourAttractions(
                selectUiState = selectUiState,
                selectViewModel = selectViewModel,
                contentPadding = PaddingValues(5.dp),
            )
        }
        Divider(thickness = 4.dp)
        Spacer(modifier = Modifier.height(2.dp))

        /** ================================================== */
        /** Tour Attraction Selection ====================*/
        Column(
        ) {
            val tourAttractionUiState =
                (tourAttractionViewModel.tourAttractionUiState as? TourAttractionUiState.TourAttractionSuccess)
            if (tourAttractionUiState != null) {
                ShowTourAttractionInfos(
                    onGpsClicked = onGpsClicked,
                    selectUiState = selectUiState,
                    selectViewModel = selectViewModel,
                    tourAttractionUiState = tourAttractionUiState,
                    selectedCountry = selectUiState.selectCountry,
                    selectedCity = selectUiState.selectCity,
                    selectedInterest = selectUiState.selectInterest,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(0.dp),
                )
            }
        }
    }
}

/** ===================================================================== */
/** ResetConfirmDialog to ask whether to reset or not ====================*/
@Composable
fun ResetConfirmDialog(
    selectViewModel: ViewModelSelect,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Text(
                text = "선택사항들 초기화 ㄱㄱ",
                fontSize = 20.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        selectViewModel.resetAllSelectUiState()
                        onDismiss()
                    }
                ) {
                    Text(text = "확인",fontSize = 20.sp,)
                }
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = "취소",fontSize = 20.sp,)
                }
            }
        },
    )
}

/** ===================================================================== */
/** PlanConfirmDialog to ask whether to go planPage or not ====================*/
@Composable
fun PlanConfirmDialog(
    planViewModel: ViewModelPlan,
    selectUiState: SelectUiState,
    onNextButtonClicked: () -> Unit = {},
    onDismiss: () -> Unit
) {
    if (selectUiState.selectDateRange == null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = "날짜 고르삼",
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                    modifier = Modifier
                        .padding(10.dp) // 원하는 여백을 추가).
                        .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                )
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = "확인",fontSize = 20.sp,)
                    }
                }
            },
        )
    } else if (selectUiState.selectTourAttractions.isEmpty()) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = "관광지 고르삼",
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                    modifier = Modifier
                        .padding(10.dp) // 원하는 여백을 추가).
                        .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                )
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = "확인",fontSize = 20.sp,)
                    }
                }
            },
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = "선택사항들로 계획표를 만듭니다",
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                    modifier = Modifier
                        .padding(10.dp) // 원하는 여백을 추가).
                        .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                )
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            planViewModel.setPlanDateRange(selectUiState.selectDateRange)
                            planViewModel.setPlanTourAttr(selectUiState.selectTourAttractions)
                            onNextButtonClicked()
                            onDismiss()
                        }
                    ) {
                        Text(text = "확인",fontSize = 20.sp,)
                    }
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = "취소",fontSize = 20.sp,)
                    }
                }
            },
        )
    }
}

/** ===================================================================== */
/** function for getting  ====================*/
fun getDatetoWeather(
    Date: SelectUiState,
){

}