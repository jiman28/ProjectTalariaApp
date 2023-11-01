package com.example.projecttravel.ui.screens.boards.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.boards.boardapi.SendArticle
import com.example.projecttravel.ui.screens.boards.boarddialogs.ArticleConfirmDialog
import com.example.projecttravel.ui.screens.boards.boarddialogs.CancelWriteArticleDialog
import com.example.projecttravel.ui.screens.login.data.UserUiState

@Composable
fun WritePageButtons(
    tabTitle: String,
    title: String,
    content: String,
    userUiState: UserUiState,
    onBackButtonClicked: () -> Unit,
) {
    val sendArticle = userUiState.currentLogin?.let {
        SendArticle(
            tabTitle = tabTitle,
            title = title,
            content = content,
            email = it.email,
        )
    }

    var isCancelWriteArticleDialog by remember { mutableStateOf(false) }
    var isAddArticleDialog by remember { mutableStateOf(false) }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog(onDismissAlert = { isLoadingState = null })
            false -> GlobalErrorDialog(onDismissAlert = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

    Column(
        verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
    ) {
        Row {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp),
                onClick = {
                    isCancelWriteArticleDialog = true
                },
                shape = RoundedCornerShape(0.dp),
            ) {
                Text(text = stringResource(R.string.cancel_button))
                if (isCancelWriteArticleDialog) {
                    CancelWriteArticleDialog(
                        onBackButtonClicked = onBackButtonClicked,
                        onDismiss = {
                            isCancelWriteArticleDialog = false
                        },
                    )
                }
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp),
                onClick = {
                    isAddArticleDialog = true
                },
                shape = RoundedCornerShape(0.dp),
            ) {
                Text(text = stringResource(R.string.confirm_button))
                if (isAddArticleDialog) {
                    if (sendArticle != null) {
                        ArticleConfirmDialog(
                            sendArticle = sendArticle,
                            onBackButtonClicked = onBackButtonClicked,
                            onDismiss = {
                                isAddArticleDialog = false
                            },
                            onLoadingStarted = {
                                isLoadingState = true
                            },
                            onErrorOccurred = {
                                isLoadingState = false
                            },
                        )
                    }
                }
            }
        }
    }
}

