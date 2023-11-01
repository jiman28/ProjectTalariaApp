package com.example.projecttravel.ui.screens.boards

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ScrollView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.model.board.AllBoards
import com.example.projecttravel.model.board.Board
import com.example.projecttravel.model.board.Company
import com.example.projecttravel.model.board.Trade
import com.example.projecttravel.ui.screens.login.data.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import org.jsoup.Jsoup

@Composable
fun ViewContentsBoard(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onContentRefreshClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    val currentBoard: AllBoards? = when (boardSelectUiState.currentSelectedBoard) {
        R.string.board -> boardSelectUiState.selectedBoardContent
        R.string.trade -> boardSelectUiState.selectedTradeContent
        R.string.company -> boardSelectUiState.selectedCompanyContent
        else -> null
    }
    val currentArticleNo: String = when (currentBoard) {
        is Board -> currentBoard.articleNo
        is Company -> currentBoard.articleNo
        is Trade -> currentBoard.articleNo
        else -> ""
    }
    val currentTitle: String = when (currentBoard) {
        is Board -> currentBoard.title
        is Company -> currentBoard.title
        is Trade -> currentBoard.title
        else -> ""
    }
    val currentContent: String = when (currentBoard) {
        is Board -> currentBoard.content
        is Company -> currentBoard.content
        is Trade -> currentBoard.content
        else -> ""
    }
    val currentWriteDate: String = when (currentBoard) {
        is Board -> currentBoard.writeDate
        is Company -> currentBoard.writeDate
        is Trade -> currentBoard.writeDate
        else -> ""
    }
    val currentViews: String = when (currentBoard) {
        is Board -> currentBoard.views
        is Company -> currentBoard.views
        is Trade -> currentBoard.views
        else -> ""
    }
    val currentWriteId: String = when (currentBoard) {
        is Board -> currentBoard.writeId
        is Company -> currentBoard.writeId
        is Trade -> currentBoard.writeId
        else -> ""
    }
    val currentUserId: String = when (currentBoard) {
        is Board -> currentBoard.userId
        is Company -> currentBoard.userId
        is Trade -> currentBoard.userId
        else -> ""
    }

    val tabtitle: String = when (boardSelectUiState.currentSelectedBoard) {
        R.string.board -> stringResource(R.string.boardTitle)
        R.string.trade -> stringResource(R.string.tradeTitle)
        R.string.company -> stringResource(R.string.companyTitle)
        else -> ""
    }

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start = 15.dp, end = 15.dp, top = 15.dp),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
        ) {
            Text(
                fontSize = 40.sp,
                text = when (boardSelectUiState.currentSelectedBoard) {
                    R.string.board -> stringResource(R.string.boardTitle)
                    R.string.trade -> stringResource(R.string.tradeTitle)
                    R.string.company -> stringResource(R.string.companyTitle)
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

        Column (
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
                Text(fontSize = 12.sp, text = currentArticleNo)
                Spacer(modifier = Modifier.padding(5.dp))

                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account"
                )
                Spacer(modifier = Modifier.padding(1.dp))
                Text(fontSize = 12.sp, text = currentWriteId)

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
                Text(fontSize = 12.sp, text = currentViews)
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

            Spacer(modifier = Modifier.padding(10.dp))
            Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            Spacer(modifier = Modifier.padding(5.dp))

//            Column {
//                userUiState.currentLogin?.id?.let { Text(text = "로그인 $it") }
//                Text(text = "게시글 $currentUserId")
//            }

            Column {
                ViewReply(
                    boardSelectUiState = boardSelectUiState,
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
