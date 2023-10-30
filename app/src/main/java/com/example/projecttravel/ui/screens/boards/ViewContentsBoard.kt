package com.example.projecttravel.ui.screens.boards

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.projecttravel.BuildConfig

@Composable
fun ViewContentsBoard(

) {






}

///** localhost IPv4 BuildConfig for WebView */
//private val BASE_BOARD_SUMMERNOTE = BuildConfig.BASE_BOARD_SUMMERNOTE
//
///** html parser for Text and Img (summernote) ====================*/
//@Composable
//fun HTMLContentWithImage(content: String) {
//    AndroidView(
//        factory = { context ->
//            WebView(context).apply {
//                // WebView 설정
//                settings.javaScriptEnabled = true // JavaScript 활성화 여부 설정
//                webViewClient = WebViewClient()
//            }
//        },
//        update = { webView ->
//            // WebView에 이미지 URL을 로드하고 HTML을 표시
//            webView.loadDataWithBaseURL(BASE_BOARD_SUMMERNOTE, content, "text/html", "utf-8", null)
//        }
//    )
//}