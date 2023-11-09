package com.example.projecttravel.ui.screens.plantrip

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
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.viewmodels.UserViewModel
import com.example.projecttravel.ui.screens.plantrip.plandialogs.ResetPlanDialog
import com.example.projecttravel.ui.screens.plantrip.plandialogs.SavePlanDialog
import com.example.projecttravel.data.viewmodels.PlanViewModel
import com.example.projecttravel.data.viewmodels.SelectViewModel

@Composable
fun PlanPageButtons (
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    planUiState: PlanUiState,
    planViewModel: PlanViewModel,
    selectViewModel: SelectViewModel,
    onCancelButtonClicked: () -> Unit,
    onPlanCompleteClicked: () -> Unit,
) {
    var isResetPlanDialogVisible by remember { mutableStateOf(false) }
    var isSavePlanDialogVisible by remember { mutableStateOf(false) }

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
            onClick = {
                isResetPlanDialogVisible = true
            }
        ) {
            Text(stringResource(R.string.cancel_button))
            if (isResetPlanDialogVisible) {
                ResetPlanDialog(
                    planViewModel = planViewModel,
                    userViewModel = userViewModel,
                    onCancelButtonClicked = onCancelButtonClicked,
                    onDismiss = {
                        isResetPlanDialogVisible = false
                    }
                )
            }
        }
        /** Sending All selections to DataBase ====================*/
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .padding(3.dp),
            onClick = {
                isSavePlanDialogVisible = true
            }
        ) {
            Text(text = "계획 다짬!")
            if (isSavePlanDialogVisible) {
                SavePlanDialog(
                    userUiState = userUiState,
                    planUiState = planUiState,
                    planViewModel = planViewModel,
                    selectViewModel = selectViewModel,
                    onPlanCompleteClicked = onPlanCompleteClicked,
                    onDismiss = {
                        isSavePlanDialogVisible = false
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
        isResetPlanDialogVisible = true
    }
}
