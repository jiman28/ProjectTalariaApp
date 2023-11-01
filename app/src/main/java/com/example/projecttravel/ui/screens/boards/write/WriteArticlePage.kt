package com.example.projecttravel.ui.screens.boards.write

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.projecttravel.ui.screens.login.data.UserUiState

@Composable
fun WriteArticlePage(
    userUiState: UserUiState,
    onBackButtonClicked: () -> Unit,
) {
    Column {

    }

    val tabTitle = "리뷰모음"
    val title = "리뷰모음안드로이드테스트"
    val content = "리뷰모음안드로이드테스트"

    WritePageButtons(
        tabTitle = tabTitle,
        title = title,
        content = content,
        userUiState = userUiState,
        onBackButtonClicked = onBackButtonClicked
    )
}