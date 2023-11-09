package com.example.projecttravel.ui.screens.boardwrite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.viewmodels.BoardPageViewModel

@Composable
fun BoardDropDownMenu(
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
){
// 1. DropDownMenu의 펼쳐짐 상태 정의
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

// 2. DropDownMenu의 Expanded 상태를 변경하기 위한 버튼 정의
    TextButton(
        modifier = Modifier,
        onClick = { isDropDownMenuExpanded = true }
    ) {
        Text(
            text = stringResource(boardPageUiState.selectedWriteBoardMenu)
        )
    }

    // 3. DropDownMenu 정의
    DropdownMenu(
        modifier = Modifier.wrapContentSize(),
        expanded = isDropDownMenuExpanded,
        offset = DpOffset(0.dp, 0.dp), // Dropdown Menu 의 위치 조정
        onDismissRequest = { isDropDownMenuExpanded = false },
    ) {
        Column(
            modifier = Modifier.clickable {
                isDropDownMenuExpanded = false // 메뉴 닫기
                boardPageViewModel.setWriteBoardMenu(R.string.boardTitle)
            },
        ) {
            Text(
                text = stringResource(R.string.boardTitle),
                fontSize = 30.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        }
        Column(
            modifier = Modifier.clickable {
                isDropDownMenuExpanded = false // 메뉴 닫기
                boardPageViewModel.setWriteBoardMenu(R.string.companyTitle)
            },
        ) {
            Text(
                text = stringResource(R.string.companyTitle),
                fontSize = 30.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        }
        Column(
            modifier = Modifier.clickable {
                isDropDownMenuExpanded = false // 메뉴 닫기
                boardPageViewModel.setWriteBoardMenu(R.string.tradeTitle)
            },
        ) {
            Text(
                text = stringResource(R.string.tradeTitle),
                fontSize = 30.sp,
                textAlign = TextAlign.Center, // 텍스트 내용 가운데 정렬
                modifier = Modifier
                    .padding(10.dp) // 원하는 여백을 추가).
                    .fillMaxWidth() // 화면 가로 전체를 차지하도록 함
            )
        }
    }
}
