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
                        HTMLContentWithImage(board.content)
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

//Column {
//    if (boardUiState != null) {
//        LazyColumn(
//            modifier = modifier,
//            contentPadding = contentPadding,
//            verticalArrangement = Arrangement.spacedBy(24.dp),
//        ) {
//            items(
//                items = boardUiState.boardList.reversed(),  // 역순 처리
//                key = { board ->
//                    board.articleNo
//                }
//            ) { board ->
//                Column {
//                    Text(text = "articleNo = ${board.articleNo}")
//                    Text(text = "content = ${board.content}")
//                    Text(text = "title = ${board.title}")
//                    Text(text = "views = ${board.views}")
//                    Text(text = "writeDate = ${board.writeDate}")
//                    Text(text = "writeId = ${board.writeId}")
//                    Text(text = "userId = ${board.userId}")
//                }
//
//                Column {
//                    HTMLContentWithImage(board.content)
//                }
//
//                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
//            }
//        }
//    }
//}
