package com.example.projecttravel.ui.screens.select

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
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.ui.screens.select.selectdialogs.CancelSelectDialog
import com.example.projecttravel.ui.screens.select.selectdialogs.PlanConfirmDialog
import com.example.projecttravel.ui.screens.select.selectdialogs.ResetConfirmDialog
import com.example.projecttravel.data.viewmodels.PlanViewModel
import com.example.projecttravel.data.viewmodels.SelectViewModel
import com.example.projecttravel.data.viewmodels.UserViewModel

@Composable
fun SelectPageButtons (
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    planViewModel: PlanViewModel,
    selectUiState: SelectUiState,
    selectViewModel: SelectViewModel,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
) {
    var isCancelSelectDialogVisible by remember { mutableStateOf(false) }

    var isResetDialogVisible by remember { mutableStateOf(false) }
    var isPlanConfirmVisible by remember { mutableStateOf(false) }

    var txtErrorMsg by remember { mutableStateOf("") }
    var isTextErrorDialog by remember { mutableStateOf(false) }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
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
            onClick = { isCancelSelectDialogVisible = true }
        ) {
            Text(stringResource(R.string.cancel_button))
            if (isCancelSelectDialogVisible) {
                CancelSelectDialog(
                    selectViewModel = selectViewModel,
                    userViewModel = userViewModel,
                    onCancelButtonClicked = onCancelButtonClicked,
                    onDismiss = {
                        isCancelSelectDialogVisible = false
                    }
                )
            }
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
                if (selectUiState.selectDateRange == null) {
                    txtErrorMsg = "날짜를 고르세요"
                    isTextErrorDialog = true
                } else if (selectUiState.selectTourAttractions.isEmpty()) {
                    txtErrorMsg = "관광지를 1개 이상 고르세요"
                    isTextErrorDialog = true
                } else {
                    isPlanConfirmVisible = true
                }
            }
        ) {
            Text(stringResource(R.string.next_button))
            if (isTextErrorDialog) {
                TextMsgErrorDialog(
                    txtErrorMsg = txtErrorMsg,
                    onDismiss = {
                        isTextErrorDialog = false
                    },
                )
            }
            if (isPlanConfirmVisible) {
                PlanConfirmDialog(
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

    /** ================================================== */
    /** Bottom BackHandler Click Action ====================*/
    if (userUiState.isBackHandlerClick) {
        isCancelSelectDialogVisible = true
    }
}
