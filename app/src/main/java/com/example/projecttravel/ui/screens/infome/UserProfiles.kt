package com.example.projecttravel.ui.screens.infome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.UserInfo
import com.example.projecttravel.model.UserResponse
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.ui.screens.TravelScreen
import com.example.projecttravel.ui.screens.infome.infoapi.getPeopleLikeMe
import com.example.projecttravel.ui.screens.infome.infodialog.UserSimilarToMeDialog
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun UserProfiles(
    filteredInfoGraph: UserInfo?,
    currentUserInfo: UserResponse,
    allBoardsCounts: Int,
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    navController: NavHostController,
) {
    val scope = rememberCoroutineScope()

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    var peopleErrorMsg by remember { mutableStateOf("") }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> TextMsgErrorDialog(
                txtErrorMsg = peopleErrorMsg,
                onDismiss = { isLoadingState = null })

            else -> isLoadingState = null
        }
    }

    var isMyHexaGraph by remember { mutableStateOf(false) }
    if (isMyHexaGraph && filteredInfoGraph != null) {
        MyInterestGraph(
            filteredInfoGraph = filteredInfoGraph,
            onDismiss = { isMyHexaGraph = false },
        )
    }

    var isSimilarToMe by remember { mutableStateOf(false) }
    if (isSimilarToMe) {
        UserSimilarToMeDialog(
            userUiState = userUiState,
            userViewModel = userViewModel,
            onUserClicked = {
                isSimilarToMe = false
                navController.navigate(TravelScreen.Page1A.name)
            },
            onDismiss = { isSimilarToMe = false },
        )
    }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
        ) {
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(currentUserInfo.picture)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.talaria),
                    placeholder = painterResource(id = R.drawable.loading_img)
                )
            }

            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.Start, // 수평 가운데 정렬
                modifier = Modifier.weight(3f).padding(1.dp).fillMaxWidth(),
            ) {
                Text(text = "Name : ${currentUserInfo.name}", modifier = Modifier.padding(5.dp))
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = "E-mail : ${currentUserInfo.email}", modifier = Modifier.padding(5.dp))
            }
            Column(
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                modifier = Modifier.weight(1f).padding(1.dp).fillMaxWidth(),
            ) {
                Button(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(2.dp),
                    onClick = {
                        isMyHexaGraph = true
//                        navController.navigate(TravelScreen.Page1B.name)
                    },
                ) {
                    Text(text = "성향")
                }
            }
        }
        Column {
            Row {
                TextButton(
                    onClick = { userViewModel.setUserPageTab(R.string.userTabMenuBoard) },
                ) {
                    Text(text = "전체 게시글 개수 : $allBoardsCounts")
                }
                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(
                    onClick = {
                        scope.launch {
                            isLoadingState = true
                            val peopleDeferred = async { getPeopleLikeMe(currentUserInfo) }
                            val peopleComplete = peopleDeferred.await()
                            if (peopleComplete.isNotEmpty()) {
                                isLoadingState = null
                                userViewModel.setLikeUsers(peopleComplete)
                                isSimilarToMe = true
                            } else {
                                peopleErrorMsg = "서버 오류"
                                isLoadingState = false
                            }
                        }
                    },
                ) {
                    Text(text = "비슷한 성향의 유저")
                }
            }
        }
    }
}