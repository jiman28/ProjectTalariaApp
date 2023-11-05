package com.example.projecttravel.ui.screens.boardwrite

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
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.TextMsgErrorDialog
import com.example.projecttravel.model.SendArticle
import com.example.projecttravel.ui.screens.boardwrite.writedialogs.ArticleConfirmDialog
import com.example.projecttravel.ui.screens.boardwrite.writedialogs.CancelWriteArticleDialog
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect

@Composable
fun WritePageButtons(
    title: String,
    content: String,
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onBackButtonClicked: () -> Unit,
) {
    val tabTitle = stringResource(boardSelectUiState.selectedWriteBoardMenu)
    val transformedContent = convertTextToHtml(content)
    val sendArticle = userUiState.currentLogin?.let {
        SendArticle(
            tabTitle = tabTitle,
            title = title,
            content = transformedContent,
            email = it.email,
        )
    }
    var txtErrorMsg by remember { mutableStateOf("") }
    var isTextErrorDialog by remember { mutableStateOf(false) }

    var isCancelWriteArticleDialog by remember { mutableStateOf(false) }
    var isAddArticleDialog by remember { mutableStateOf(false) }

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
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
                        boardSelectViewModel = boardSelectViewModel,
                        userViewModel = userViewModel,
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
                    if (boardSelectUiState.selectedWriteBoardMenu == R.string.selectMenu) {
                        txtErrorMsg = "게시판을 고르세요"
                        isTextErrorDialog = true
                    } else if (title == "") {
                        txtErrorMsg = "제목을 적으세요"
                        isTextErrorDialog = true
                    } else if (content == "") {
                        txtErrorMsg = "내용을 작성하세요"
                        isTextErrorDialog = true
                    } else {
                        isAddArticleDialog = true
                    }
                },
                shape = RoundedCornerShape(0.dp),
            ) {
                Text(text = stringResource(R.string.confirm_button))
                if (isAddArticleDialog) {
                    if (sendArticle != null) {
                        ArticleConfirmDialog(
                            sendArticle = sendArticle,
                            boardSelectUiState = boardSelectUiState,
                            boardSelectViewModel = boardSelectViewModel,
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
                if (isTextErrorDialog) {
                    TextMsgErrorDialog(
                        txtErrorMsg = txtErrorMsg,
                        onDismiss = {
                            isTextErrorDialog = false
                        },
                    )
                }
            }
        }
    }

    /** ================================================== */
    /** Bottom BackHandler Click Action ====================*/
    if (userUiState.isBackHandlerClick) {
        isCancelWriteArticleDialog = true
    }
}

fun convertTextToHtml(text: String): String {
    val lines = text.split("\n")
    val htmlLines = lines.map { "<p>$it</p>" }
    return htmlLines.joinToString("")
}
