package com.example.projecttravel.ui.screens.select

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.data.uistates.viewmodels.SelectViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

@Composable
fun SelectDateRange(
    selectUiState: SelectUiState,
    selectViewModel: SelectViewModel = viewModel(),
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
    selectViewModel: SelectViewModel = viewModel(),
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
                DateRangePickerCustom(
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
fun DateRangePickerCustom(
    selectViewModel: SelectViewModel,
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

    DatePickerDialog(
        shape = RoundedCornerShape(6.dp),
        onDismissRequest = onDismiss,
        confirmButton = {},
    ) {
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
                Text(
                    text = stringResource(R.string.close_button),
                    fontSize = 10.sp
                )
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
                Text(
                    text = stringResource(R.string.reset_button),
                    fontSize = 10.sp
                )
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
                Text(
                    text = stringResource(R.string.confirm_button),
                    fontSize = 10.sp
                )
            }
        }

        /** DateRangePicker Calendar */
        /** How to Edit DateRangePicker */
        DateRangePicker(
            state = dateRangePickerState,
            /** title -> Top level title */
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                    horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Select Your Trip Date Range",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            },
            /** headline -> Shows Your DateRange Selection */
            headline = {
                Row(
                    verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                    horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                        modifier = Modifier.fillMaxWidth().weight(1f),   // 경우에 따라 -> .fillMaxHeight()
                    ) {
                        (if (dateRangePickerState.selectedStartDateMillis != null)
                            dateRangePickerState.selectedStartDateMillis?.let {
                                getFormattedDate(it)
                            } else "Start Date")?.let { Text(text = it ,fontSize = 20.sp) }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                        modifier = Modifier.fillMaxWidth().weight(0.2f),   // 경우에 따라 -> .fillMaxHeight()
                    ) {
                        Text(text = "~",fontSize = 20.sp)
                    }
                    Column(
                        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                        modifier = Modifier.fillMaxWidth().weight(1f),   // 경우에 따라 -> .fillMaxHeight()
                    ) {
                        (if (dateRangePickerState.selectedEndDateMillis != null)
                            dateRangePickerState.selectedEndDateMillis?.let {
                                getFormattedDate(it)
                            } else "End Date")?.let { Text(text = it,fontSize = 20.sp) }
                    }
                }
            },
            showModeToggle = false,
            modifier = Modifier
                .size(600.dp),
        )
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

/** Original Data from DateRangePicker is ~년~월~일 */
fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("yyyy/MM/dd")
    return dateFormat.format(calender.timeInMillis)
}


/**
 *<Data appearances>
 *  selectViewModel.setDateRange = (startDate..endDate)  =>  ex) 2023.01.01..2023.01.02
 *  dateRangePickerState.selectedStartDateMillis =>  startDate   =>  selectUiState.selectDateRange.start
 *  dateRangePickerState.selectedEndDateMillis   =>  endDate     =>  selectUiState.selectDateRange.endInclusive
 *<When reset selectedStartDateMillis & selectedEndDateMillis>
 *  dateRangePickerState.setSelection(null,null)
 */

