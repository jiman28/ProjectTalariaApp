package com.example.projecttravel.ui.screens.myinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.ui.screens.login.data.UserUiState
import com.example.projecttravel.ui.screens.login.data.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.user.UserInfoUiState

@Composable
fun MyHexaGraph(
    modifier: Modifier = Modifier,
    userInfoUiState: UserInfoUiState.UserInfoSuccess,
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            items = userInfoUiState.userInfoList,
            key = { userInfo ->
                userInfo.user
            }
        ) { userInfo ->
            Column {
                Text(text = "user = ${userInfo.user}")
                Text(text = "id = ${userInfo.id}")
                Text(text = "culture = ${userInfo.culture}")
                Text(text = "food = ${userInfo.food}")
                Text(text = "history = ${userInfo.history}")
                Text(text = "nature = ${userInfo.nature}")
                Text(text = "reliability = ${userInfo.reliability}")
                Text(text = "religion = ${userInfo.religion}")
                Text(text = "sights = ${userInfo.sights}")
            }
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
        }
    }
}
