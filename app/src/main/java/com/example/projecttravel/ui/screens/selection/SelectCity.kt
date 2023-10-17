package com.example.projecttravel.ui.screens.selection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.select.CityInfo
import com.example.projecttravel.model.select.CountryInfo
import com.example.projecttravel.ui.screens.viewmodels.selection.CityUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect

@Composable
fun SelectCity(
    selectUiState: SelectUiState,
    cityUiState: CityUiState.CitySuccess,
    selectViewModel: ViewModelSelect = viewModel(),
    selectedCountry: CountryInfo?,
    selectedCity: CityInfo?,
) {
    CityDropDownMenu(
        selectUiState = selectUiState,
        selectViewModel = selectViewModel,
        selectedCity = selectedCity,
        selectedCountry = selectedCountry,
        cityList = cityUiState.cityList,
    )
}

@Composable
fun CityDropDownMenu(
    selectUiState: SelectUiState,
    selectViewModel: ViewModelSelect = viewModel(),
    selectedCountry: CountryInfo?,
    selectedCity: CityInfo?, // 선택된 나라 정보 받기
    cityList: List<CityInfo>,
) {
    // 1. DropDownMenu의 펼쳐짐 상태 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    // 2. DropDownMenu의 Expanded 상태를 변경하기 위한 버튼 정의
    Button(
        modifier = Modifier.size(width = 110.dp, height = 40.dp),
        onClick = { isDropDownMenuExpanded = true }
    ) {
        Text(
            text = selectUiState.selectCity?.cityName ?: "도시 고르기"
        )
    }

    // 3. DropDownMenu 정의
    DropdownMenu(
        modifier = Modifier.wrapContentSize(),
        expanded = isDropDownMenuExpanded,
        offset = DpOffset((-10).dp, 0.dp), // Dropdown Menu 의 위치 조정
        onDismissRequest = { isDropDownMenuExpanded = false },
    ) {
        if (selectedCountry != null) {
            cityList
                .filter { it.countryId == selectedCountry.countryId }
                .forEach { city ->
                    Column(
                        modifier = Modifier.selectable(
                            selected = selectedCity == city,
                            onClick = {
                                selectViewModel.setCity(city) // 변경된 CountryInfo를 전달
                                isDropDownMenuExpanded = false // 메뉴 닫기
                            }
                        ),
                    ) {
                        Text(
                            text = city.cityName,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                            modifier = Modifier
                                .padding(10.dp) // 원하는 여백을 추가).
                                .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                        )
                    }
                }
        } else {
            AlertDialog(
                onDismissRequest = { isDropDownMenuExpanded = false },
                text = {
                    Text(
                        text = "나라를 선택하세요",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                        modifier = Modifier
                            .padding(10.dp) // 원하는 여백을 추가).
                            .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isDropDownMenuExpanded = false
                        }
                    ) {
                        Text(text = "확인",fontSize = 20.sp,)
                    }
                }
            )
//            Text(
//                text = "나라를 선택하세요",
//                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
//                modifier = Modifier
//                    .padding(10.dp) // 원하는 여백을 추가).
//                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
//            )
        }
    }
}
