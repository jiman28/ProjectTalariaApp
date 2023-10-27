package com.example.projecttravel.ui.screens.userinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.projecttravel.auth.login.data.UserUiState
import com.example.projecttravel.auth.login.data.ViewModelUser

@Composable
fun UserPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
) {
    if (userUiState.currentLogin != null){
        Column {
            Text(text = userUiState.currentLogin.name)
            Text(text = userUiState.currentLogin.id)
            Text(text = userUiState.currentLogin.email)
            if (userUiState.currentLogin.picture != null) Text(text = userUiState.currentLogin.picture)
        }

    }
}