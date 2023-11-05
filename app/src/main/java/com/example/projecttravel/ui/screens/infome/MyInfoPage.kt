package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.user.UserInfoUiState
import com.example.projecttravel.ui.screens.viewmodels.user.ViewModelListUserInfo

@Composable
fun MyInfoPage(
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
) {
    val userInfoListViewModel: ViewModelListUserInfo = viewModel(factory = ViewModelListUserInfo.UserInfoFactory)

    Column {
        Column {
            if (userUiState.checkOtherUser != null) {
                Column {

                    Text(text = "현재 확인 중인 아이디", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 이름${userUiState.checkOtherUser.name}", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 아이디${userUiState.checkOtherUser.id}", modifier = Modifier.padding(5.dp))
                    Text(text = "현재 이메일${userUiState.checkOtherUser.email}", modifier = Modifier.padding(5.dp))
                    if (userUiState.checkOtherUser.picture != null) Text(text = userUiState.checkOtherUser.picture)
                }
            }

            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

            /** ================================================== */
            /** UserInfos */
            Column(
                modifier = Modifier
                    .padding(3.dp),
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
            ) {
                val userInfoUiState =
                    (userInfoListViewModel.userInfoUiState as? UserInfoUiState.UserInfoSuccess)
                if (userInfoUiState != null) {
                    MyHexaGraph(
                        userInfoUiState = userInfoUiState,
                        userUiState = userUiState,
                        userViewModel = userViewModel,
                        contentPadding = PaddingValues(0.dp),
                    )
                }
            }
        }
    }
}
