package com.example.projecttravel.ui.screens.boardwrite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect

@Composable
fun WriteArticlePage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onBackButtonClicked: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
    ) {
        Column {
            WritePageButtons(
                title = title,
                content = content,
                userUiState = userUiState,
                userViewModel = userViewModel,
                boardSelectUiState = boardSelectUiState,
                boardSelectViewModel = boardSelectViewModel,
                onBackButtonClicked = onBackButtonClicked
            )
        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        Column {
            Text(text = "게시글 작성")
//            Text(text = "R.string 확인 ${R.string.selectMenu}")
        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Column (
                modifier = Modifier.weight(3f)
            ) {
                Text(text = "게시판 선택")
            }
            Column (
                modifier = Modifier.weight(8f)
            ) {
                BoardDropDownMenu(
                    boardSelectUiState = boardSelectUiState,
                    boardSelectViewModel = boardSelectViewModel,
                )
            }
        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Column (
                modifier = Modifier.weight(2f)
            ) {
                Text(text = "제목")
            }
            Column (
                modifier = Modifier.weight(8f)
            ) {
                val focusManager = LocalFocusManager.current
                TextField(
                    value = title,
                    onValueChange = { newValue -> title = newValue },
                    label = { Text(text = "게시판 제목") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                )
            }
        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        Column {
            Column {
                Text(text = "내용 작성")
            }
            Column {
                TextField(
                    value = content,
                    onValueChange = { newValue -> content = newValue },
                    label = { Text(text = "게시판 내용") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {  }
                    ),
                )
            }
        }
    }
}