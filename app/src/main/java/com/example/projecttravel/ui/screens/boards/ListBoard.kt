package com.example.projecttravel.ui.screens.boards

import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.R
import com.example.projecttravel.ui.screens.viewmodels.board.BoardUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListBoard
import org.jsoup.Jsoup

@Composable
fun ListBoard(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    val boardListViewModel: ViewModelListBoard =
        viewModel(factory = ViewModelListBoard.BoardFactory)
    val boardUiState = (boardListViewModel.boardUiState as? BoardUiState.BoardSuccess)

    Column {
        if (boardUiState != null) {
            LazyColumn(
                modifier = modifier,
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                items(
                    items = boardUiState.boardList.reversed(),  // 역순 처리
                    key = { board ->
                        board.articleNo
                    }
                ) { board ->
                    Column {
                        Text(text = "articleNo = ${board.articleNo}")
                        Text(text = "content = ${board.content}")
                        Text(text = "title = ${board.title}")
                        Text(text = "views = ${board.views}")
                        Text(text = "writeDate = ${board.writeDate}")
                        Text(text = "writeId = ${board.writeId}")
                        Text(text = "userId = ${board.userId}")
                    }

                    Column {
                        // HTML 문자열을 파싱하여 DOM 생성
                        val doc = Jsoup.parse(board.content)

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

                    Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
                }
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

