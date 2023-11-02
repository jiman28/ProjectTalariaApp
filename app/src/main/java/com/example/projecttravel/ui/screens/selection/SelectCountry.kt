package com.example.projecttravel.ui.screens.selection

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.CountryInfo
import com.example.projecttravel.ui.screens.viewmodels.selection.CountryUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset

private const val TAG = "AAAAA"

@Composable
fun SelectCountry(
    selectUiState: SelectUiState,
    countryUiState: CountryUiState.CountrySuccess,
    selectViewModel: ViewModelSelect,
    selectedCountry: CountryInfo?,
) {
    CountryDropDownMenu(
        selectUiState = selectUiState,
        selectViewModel = selectViewModel,
        selectedCountry = selectedCountry,
        countryList = countryUiState.countryList,
    )
}

/** for 문을 사용한 DropDownMenu List의  분할 */
@Composable
fun CountryDropDownMenu(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect,
    selectedCountry: CountryInfo?, // 선택된 나라 정보 받기
    countryList: List<CountryInfo>,
) {
    // 1. DropDownMenu의 펼쳐짐 상태 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    // 2. DropDownMenu의 Expanded 상태를 변경하기 위한 버튼 정의
    Button(
        modifier = Modifier.size(width = 110.dp, height = 40.dp),
        onClick = { isDropDownMenuExpanded = true }
    ) {
        Text(
            text = selectUiState.selectCountry?.countryName ?: "나라 고르기" // 선택된 나라가 없으면 "나라 고르기"를 표시
        )
    }

    // 3. DropDownMenu 정의
    DropdownMenu(
        modifier = Modifier.wrapContentSize(),
        expanded = isDropDownMenuExpanded,
        offset = DpOffset(35.dp, 0.dp), // Dropdown Menu 의 위치 조정
        onDismissRequest = { isDropDownMenuExpanded = false }
    ) {
        if (countryList.isNotEmpty()) {
            Row {
                val groupSize = 10
                /** Number of Columns based by groupSize */
                for (groupIndex in 0 until (countryList.size + groupSize - 1) / groupSize) {
                    Column (
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                    ) {
                        /** Number of Indexes for each Columns = groupSize */
                        for (index in groupIndex * groupSize until minOf((groupIndex + 1) * groupSize, countryList.size)) {
                            val country = countryList[index]
                            Row(
                                modifier = Modifier.selectable(
                                    selected = selectedCountry == country,
                                    onClick = {
                                        selectViewModel.setCountry(country) // 변경된 CountryInfo를 전달
                                        selectViewModel.setCity(null) // 나라를 변경하면 다른 값들 초기화
                                        selectViewModel.setInterest(null) // 나라를 변경하면 다른 값들 초기화
                                        isDropDownMenuExpanded = false // 메뉴 닫기

                                        Log.d(TAG, selectUiState.selectCountry?.countryName.toString())

                                    }
                                ),
                            ) {
                                Text(
                                    text = country.countryName,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                                    modifier = Modifier
                                        .padding(3.dp) // 원하는 여백을 추가
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Text(
                text = "나라 목록이 비어 있습니다.\n인터넷 연결상태를 확인하세요",
                textAlign = TextAlign.Center,
                )
        }
    }
}
