package com.example.projecttravel.ui.screens.searchplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.SearchUiState
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.data.uistates.viewmodels.SearchViewModel
import com.example.projecttravel.data.uistates.viewmodels.SelectViewModel

@Composable
fun SearchGpsPageButton(
    selectUiState: SelectUiState,
    searchViewModel: SearchViewModel,
    selectViewModel: SelectViewModel,
    onBackButtonClicked: () -> Unit = {},
    updateUiPageClicked: () -> Unit = {},
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
//                    sendInOut(
//                        placeName = searchUiState.searchedPlace?.name,
//                        stateInOut = stateInOut
//                    )
                    selectViewModel.addTourAttrSearch(selectUiState.selectSearch)
                    selectViewModel.setSearch(null)
                    searchViewModel.resetAllSearchUiState()
                    onBackButtonClicked()
                }
            }) {
            Text(stringResource(R.string.confirm_button))
        }
    }
}


