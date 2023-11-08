package com.example.projecttravel.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.model.CountryInfo
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.LoginErrorDialog
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.homepage.HomepageViewModel

@Composable
fun HomePage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    onLogOutClicked: () -> Unit,
    countryCardClicked: (CountryInfo) -> Unit,
    onNextButtonClicked: () -> Unit,    // 매개변수 추가
    modifier: Modifier = Modifier,
) {
    if (userUiState.currentLogin == null) {
        Surface {
            LoginErrorDialog(
                userViewModel = userViewModel,
                onLogOutClicked = onLogOutClicked
            )
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_small)
            )
        ) {
            StartSelectionButton(
                onClick = { onNextButtonClicked() }
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_small)
            )
        ) {
            Text(text = "사진을 누르면 나라 정보를 볼수 있어요")
        }
        Column(
        ) {
            val homepageViewModel: HomepageViewModel = viewModel(factory = HomepageViewModel.Factory)
            ShowCountryInfos(
                homepageUiState = homepageViewModel.homepageUiState,
                countryCardClicked = countryCardClicked,
                retryAction = homepageViewModel::getCountry,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp)
            )
        }
    }
}

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun StartSelectionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(text = "Let's go Trip!!")
    }
}
