package com.example.projecttravel.ui.screens.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.CountryInfo
import com.example.projecttravel.ui.screens.viewmodels.selection.CountryUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.CountryViewModel
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect
import com.example.projecttravel.model.CityInfo
import com.example.projecttravel.model.InterestInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.selection.CityUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.CityViewModel
import com.example.projecttravel.ui.screens.viewmodels.selection.InterestUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.InterestViewModel
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttractionUiState
import com.example.projecttravel.ui.screens.viewmodels.selection.TourAttractionViewModel

private const val TAG = "AAAAA"

@Composable
fun SelectPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    planUiState: PlanUiState,
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

    Column {
        /** Buttons ====================*/
        Column {
            SelectPageButtons(
                userUiState = userUiState,
                userViewModel = userViewModel,
                planUiState = planUiState,
                planViewModel = planViewModel,
                selectUiState = selectUiState,
                selectViewModel = selectViewModel,
                onCancelButtonClicked = onCancelButtonClicked,
                onNextButtonClicked = onNextButtonClicked,
            )
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
