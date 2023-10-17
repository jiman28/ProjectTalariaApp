package com.example.projecttravel.ui.screens.selection

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun SelectDateRange(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect = viewModel(),
) {
    DateRangePickerMenu(
        selectUiState = selectUiState,
        selectViewModel = selectViewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerMenu(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect = viewModel(),
) {
    val dateRangePickerState = rememberDateRangePickerState()
    var isOpenDateRangePickerDialog by remember { mutableStateOf(false) }

    /** DateRangePickerMenu Screen closed when click and show selectedDateRange */
    Row {
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .padding(3.dp),
            onClick = { isOpenDateRangePickerDialog = true }
        ) {
            if (selectUiState.selectDateRange != null) {
                Text(
                    text = "${selectUiState.selectDateRange.start} ~ ${selectUiState.selectDateRange.endInclusive}"
                )
            } else {
                Text(text = "여행 날짜 고르기")
            }
            if (isOpenDateRangePickerDialog) {
                DateRangePickerDialog(
                    selectViewModel = selectViewModel,
                    dateRangePickerState = dateRangePickerState,
                    onDismiss = {
                        isOpenDateRangePickerDialog = false
                    }
                )
            }
        }
    }
}

/** ===================================================================== */
/** DateRangePickerDialog to choose date for trip ====================*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    selectViewModel: ViewModelSelect,
    dateRangePickerState: DateRangePickerState,
    onDismiss: () -> Unit,
) {
    /** Creating LocalContext when needing 'context' */
    val dateRangeContext = LocalContext.current  // context 생성하는 방법!!

    /** AlertDialog shows when you do not choose DateRange */
    val dateRangeAlertDialog = AlertDialog.Builder(dateRangeContext)
        .setMessage("여행 날짜를 선택하세요")
        .setPositiveButton("확인") { dialog, which -> }
        .create()

    AlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
        ) {
            Column {

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    /** Cancel DateRangeMenu Button ====================*/
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp),
                        onClick = { onDismiss() }
                    ) {
                        Text(stringResource(R.string.close_button))
                    }
                    /** Reset DateRange Button ====================*/
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(3.dp),
                        onClick = {
                            dateRangePickerState.setSelection(
                                null,
                                null
                            )  // 단순히 dateRangePickerState 의 날짜만 초기화
                            selectViewModel.setDateRange(null)
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
                            val startDate = dateRangePickerState.selectedStartDateMillis?.let {
                                millisToLocalDate(it)
                            }
                            val endDate = dateRangePickerState.selectedEndDateMillis?.let {
                                millisToLocalDate(it)
                            }
                            if (startDate != null && endDate != null) {
                                selectViewModel.setDateRange(startDate..endDate)
                                onDismiss()
                            } else {
                                dateRangeAlertDialog.show()
                            }
                        }
                    ) {
                        Text(stringResource(R.string.confirm_button))
                    }
                }

                /** DateRangePicker Calendar*/
                DateRangePicker(
                    state = dateRangePickerState,
                    modifier = Modifier
                        .padding(3.dp),
                )
            }
        }
    }
    /** DateRangePickerMenu Screen closed when click phone's backButton */
    /** Must be placed inside 'if (isDatePickerVisible)' to apply this BackHandler logic to this Composable */
    BackHandler(
        onBack = { onDismiss() }
    )
}

/** Original Data from DateRangePicker is milliseconds data */
/** Must use function turning millis into LocalDate to use SelectUiState */
fun millisToLocalDate(millis: Long): LocalDate {
    return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
}

/**
 *<Data appearances>
 *  selectViewModel.setDateRange = (startDate..endDate)  =>  ex) 2023.01.01..2023.01.02
 *  dateRangePickerState.selectedStartDateMillis =>  startDate   =>  selectUiState.selectDateRange.start
 *  dateRangePickerState.selectedEndDateMillis   =>  endDate     =>  selectUiState.selectDateRange.endInclusive
 *<When reset selectedStartDateMillis & selectedEndDateMillis>
 *  dateRangePickerState.setSelection(null,null)
 */
