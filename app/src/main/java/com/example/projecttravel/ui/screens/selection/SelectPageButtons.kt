package com.example.projecttravel.ui.screens.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.selection.selectdialogs.PlanConfirmDialog
import com.example.projecttravel.ui.screens.selection.selectdialogs.ResetConfirmDialog
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect

@Composable
fun SelectPageButtons (
    planUiState: PlanUiState,
    planViewModel: ViewModelPlan,
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
) {
    var isResetDialogVisible by remember { mutableStateOf(false) }
    var isPlanConfirmVisible by remember { mutableStateOf(false) }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog( onDismiss = { isLoadingState = null } )
            false -> GlobalErrorDialog( onDismiss = { isLoadingState = null } )
            else -> isLoadingState = null
        }
    }

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
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    selectUiState = selectUiState,
                    onNextButtonClicked = onNextButtonClicked,
                    onDismiss = {
                        isPlanConfirmVisible = false
                    },
                    onLoadingStarted = {
                        isLoadingState = true
                    },
                    onErrorOccurred = {
                        isLoadingState = false
                    },
                )
            }
        }
    }
}
