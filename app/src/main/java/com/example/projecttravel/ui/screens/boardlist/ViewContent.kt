package com.example.projecttravel.ui.screens.boardlist

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.data.uistates.viewmodels.UserViewModel
import com.example.projecttravel.model.BoardEntity
import com.example.projecttravel.model.CompanyEntity
import com.example.projecttravel.model.RemoveArticle
import com.example.projecttravel.model.TradeEntity
import com.example.projecttravel.model.UserDto
import com.example.projecttravel.ui.screens.GlobalErrorDialog
import com.example.projecttravel.ui.screens.GlobalLoadingDialog
import com.example.projecttravel.ui.screens.boardlist.boarddialogs.RemoveArticleDialog
import com.example.projecttravel.ui.screens.boardview.UserDropDownMenu
import com.example.projecttravel.ui.screens.boardview.ViewReply
import org.jsoup.Jsoup

@Composable
fun ViewContentsBoard(
    userUiState: UserUiState,
    userViewModel: UserViewModel,
    boardPageUiState: BoardPageUiState,
    boardPageViewModel: BoardPageViewModel,
    onContentRefreshClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    onUserButtonClicked: () -> Unit,
) {

    val currentArticleNo: Int = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.articleNo
        is CompanyEntity -> boardPageUiState.selectedViewBoard.articleNo
        is TradeEntity -> boardPageUiState.selectedViewBoard.articleNo
        else -> 0
    }
    val currentTitle: String = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.title
        is CompanyEntity -> boardPageUiState.selectedViewBoard.title
        is TradeEntity -> boardPageUiState.selectedViewBoard.title
        else -> ""
    }
    val currentContent: String = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.content
        is CompanyEntity -> boardPageUiState.selectedViewBoard.content
        is TradeEntity -> boardPageUiState.selectedViewBoard.content
        else -> ""
    }
    val currentWriteDate: String = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.writeDate
        is CompanyEntity -> boardPageUiState.selectedViewBoard.writeDate
        is TradeEntity -> boardPageUiState.selectedViewBoard.writeDate
        else -> ""
    }
    val currentViews: Int = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.views
        is CompanyEntity -> boardPageUiState.selectedViewBoard.views
        is TradeEntity -> boardPageUiState.selectedViewBoard.views
        else -> 0
    }
    val currentWriteId: String = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.writeId
        is CompanyEntity -> boardPageUiState.selectedViewBoard.writeId
        is TradeEntity -> boardPageUiState.selectedViewBoard.writeId
        else -> ""
    }
    val currentUser: UserDto? = when (boardPageUiState.selectedViewBoard) {
        is BoardEntity -> boardPageUiState.selectedViewBoard.user
        is CompanyEntity -> boardPageUiState.selectedViewBoard.user
        is TradeEntity -> boardPageUiState.selectedViewBoard.user
        else -> null
    }

    val tabtitle = stringResource(boardPageUiState.currentSelectedBoardTab)

    var isLoadingState by remember { mutableStateOf<Boolean?>(null) }
    Surface {
        when (isLoadingState) {
            true -> GlobalLoadingDialog()
            false -> GlobalErrorDialog(onDismiss = { isLoadingState = null })
            else -> isLoadingState = null
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start = 15.dp, end = 15.dp, top = 15.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
        ) {
            Text(
                fontSize = 40.sp,
                text = when (boardPageUiState.currentSelectedBoardTab) {
                    R.string.boardTabTitle -> stringResource(R.string.boardTabTitle)
                    R.string.tradeTabTitle -> stringResource(R.string.tradeTabTitle)
                    R.string.companyTabTitle -> stringResource(R.string.companyTabTitle)
                    else -> ""
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                fontSize = 25.sp,
                text = "게시판입니다"
            )
        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        Column(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
        ) {
            Column {
                Text(fontSize = 25.sp, text = currentTitle)
            }

            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(fontSize = 12.sp, text = currentArticleNo.toString())
                Spacer(modifier = Modifier.padding(5.dp))

                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account"
                )
                Spacer(modifier = Modifier.padding(1.dp))
                UserDropDownMenu(
                    boardPageUiState = boardPageUiState,
                    boardPageViewModel = boardPageViewModel,
                    userViewModel = userViewModel,
                    onUserButtonClicked = onUserButtonClicked,
                )
//                Text(fontSize = 12.sp, text = currentWriteId)
                Spacer(modifier = Modifier.padding(5.dp))

                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = "WriteDate"
                )
                Spacer(modifier = Modifier.padding(1.dp))
                Text(fontSize = 12.sp, text = currentWriteDate)

                Spacer(modifier = Modifier.padding(5.dp))

                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Filled.RemoveRedEye,
                    contentDescription = "Views"
                )
                Spacer(modifier = Modifier.padding(1.dp))
                Text(fontSize = 12.sp, text = currentViews.toString())
            }

            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

            Column {
                // HTML 문자열을 파싱하여 DOM 생성
                val doc = Jsoup.parse(currentContent)
                // 이미지 태그를 찾아 스타일을 추가하거나 크기를 조절
                val imgTags = doc.select("img")
                for (imgTag in imgTags) {
                    imgTag.attr("style", "max-width:100%; height:auto;") // 이미지 크기 조절 스타일 추가
                    // 다른 스타일을 추가하거나 수정할 수 있습니다.
                }
                // 수정된 HTML 문자열 생성
                val modifiedContent = doc.toString()
                // Compose의 WebView에 표시
                HTMLContentWithImage(modifiedContent)
            }
            Spacer(modifier = Modifier.padding(5.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                horizontalAlignment = Alignment.End, // 수평 가운데 정렬
            ) {
                var isRemoveArticleDialog by remember { mutableStateOf(false) }
                if (userUiState.currentLogin?.id == currentUser?.id.toString()) {
                    Button(
                        onClick = { isRemoveArticleDialog = true }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                            horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                        ) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "CancelComment"
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(text = "게시글 삭제")
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                if (isRemoveArticleDialog) {
                    val removeArticle = RemoveArticle(
                        tabTitle = tabtitle,
                        articleNo = currentArticleNo.toString(),
                    )
                    RemoveArticleDialog(
                        removeArticle = removeArticle,
                        boardPageUiState = boardPageUiState,
                        boardPageViewModel = boardPageViewModel,
                        onBackButtonClicked = onBackButtonClicked,
                        onDismiss = {
                            isRemoveArticleDialog = false
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
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            Spacer(modifier = Modifier.padding(5.dp))
            Column {
                ViewReply(
                    boardPageUiState = boardPageUiState,
                    boardPageViewModel = boardPageViewModel,
                    userUiState = userUiState,
                    currentArticleNo = currentArticleNo,
                    onContentRefreshClicked = onContentRefreshClicked,
                )
            }
        }
    }
}

/** localhost IPv4 BuildConfig for WebView */
private val BASE_BOARD_SUMMERNOTE = BuildConfig.BASE_BOARD_SUMMERNOTE

/** html parser for Text and Img (summernote) ====================*/
@Composable
fun HTMLContentWithImage(content: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // WebView 설정
                settings.javaScriptEnabled = true // JavaScript 활성화 여부 설정
                webViewClient = WebViewClient()
            }
        },
        update = { webView ->
            // WebView에 이미지 URL을 로드하고 HTML을 표시
            webView.loadDataWithBaseURL(BASE_BOARD_SUMMERNOTE, content, "text/html", "utf-8", null)
        }
    )
}
