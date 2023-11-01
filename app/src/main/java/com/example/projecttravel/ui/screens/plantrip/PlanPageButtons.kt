package com.example.projecttravel.ui.screens.plantrip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
import com.example.projecttravel.ui.screens.plantrip.plandialogs.ResetPlanDialog
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan

@Composable
fun PlanPageButtons (
    planViewModel: ViewModelPlan,
    onCancelButtonClicked: () -> Unit,
    onPlanCompleteClicked: () -> Unit,
) {
    var isResetPlanDialogVisible by remember { mutableStateOf(false) }
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

            }
        ) {
//                    Text(stringResource(R.string.reset_button))
            Text(text = "계획 다짬!")
        }
    }
}
