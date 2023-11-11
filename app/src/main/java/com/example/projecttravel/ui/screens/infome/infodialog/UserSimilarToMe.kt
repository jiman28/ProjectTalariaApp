package com.example.projecttravel.ui.screens.infome.infodialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.model.CheckOtherUserById
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.infome.infoapi.getUserPageById
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun UserSimilarToMeDialog(
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    onUserClicked: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            if (userUiState.checkLikeUsers.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    items(
                        items = userUiState.checkLikeUsers,
                        key = { people ->
                            people.id
                        }
                    ) { people ->
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(8.dp)
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
                                            .data(people.picture)
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
                                    modifier = Modifier
                                        .weight(2f)
                                        .padding(1.dp)
                                        .fillMaxWidth(),
                                ) {
                                    Text(
                                        text = " ${people.name}",
                                        modifier = Modifier.padding(5.dp)
                                    )
//                                Spacer(modifier = Modifier.padding(2.dp))
//                                Text(text = "E-mail : ${people.email}", modifier = Modifier.padding(5.dp))
                                }
                                Column(
                                    verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                                    modifier = Modifier
                                        .weight(2f)
                                        .padding(1.dp)
                                        .fillMaxWidth(),
                                ) {
                                    TextButton(
                                        modifier = Modifier
                                            .padding(2.dp),
                                        onClick = {
                                            scope.launch {
                                                val checkOtherUserById =
                                                    CheckOtherUserById(id = people.id)
                                                isLoadingState = true
                                                // 비동기 작업을 시작하고 결과(return)를 받아오기 위한 Deferred 객체를 생성합니다.
                                                val otherIdDeferred =
                                                    async { getUserPageById(checkOtherUserById) }
                                                // Deferred 객체의 await() 함수를 사용하여 작업 완료를 대기하고 결과를 받아옵니다.
                                                val isOtherIdComplete = otherIdDeferred.await()
                                                // 모든 작업이 완료되었을 때만 실행합니다.
                                                if (isOtherIdComplete != null) {
                                                    isLoadingState = null
                                                    userViewModel.setUserPageInfo(isOtherIdComplete)
                                                    userViewModel.previousScreenWasPageOneA(true)
                                                    onUserClicked()
                                                } else {
                                                    isLoadingState = false
                                                }
                                            }
                                        },
                                    ) {
                                        Text(text = "확인")
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Text(text = "비슷한 사람 없음")
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text(text = "확인", fontSize = 20.sp)
                }
            }
        },
    )
}
