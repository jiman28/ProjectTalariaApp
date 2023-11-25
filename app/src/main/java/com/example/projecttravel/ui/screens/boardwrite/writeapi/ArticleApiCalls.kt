package com.example.projecttravel.ui.screens.boardwrite.writeapi

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.data.RetrofitBuilderString
import com.example.projecttravel.model.RemoveArticle
import com.example.projecttravel.model.SendArticle
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

///** 텍스트를 일정 길이로 자르고 "..."을 추가하는 Composable 함수 */
//@Composable
//fun EllipsisTextBoard(
//    text: String,
//    maxLength: Int,
//    modifier: Modifier = Modifier
//) {
//    val displayText = if (text.length > maxLength) {
//        text.substring(0, maxLength) + "..."
//    } else {
//        text
//    }
//    Text(text = displayText, fontSize = 20.sp, modifier = modifier)
//}


/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
// send Article To Db
suspend fun sendArticleToDb(
    sendArticle: SendArticle,
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.sendArticle(sendArticle)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        Log.d("jiman=111", "Request Success + Response Success")
                        Log.d("jiman=111", call.toString())
                        Log.d("jiman=111", response.body().toString())
                        continuation.resume(true) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("jiman=222", "Response body is null")
                        continuation.resume(false) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("jiman=333", "Failure")
                    continuation.resume(false) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
                continuation.resume(false) // 작업 실패 시 false 반환
            }
        })
    }
}

///** ======================================================================================== */
///** asynchronous codes ===================================================================== */
//// remove Article From Db
//suspend fun removeArticleFromDb (
//    removeArticle: RemoveArticle,
//): Boolean {
//    return suspendCancellableCoroutine { continuation ->
//        val call = RetrofitBuilderJson.travelJsonApiService.removeArticle(removeArticle)
//        call.enqueue(object : Callback<Boolean> {
//            override fun onResponse(
//                call: Call<Boolean>,
//                response: Response<Boolean>
//            ) {
//                if (response.isSuccessful) {
//                    val loginResponse = response.body()
//                    if (loginResponse != null) {
//                        Log.d("jiman=111", "Request Success + Response Success")
//                        Log.d("jiman=111", call.toString())
//                        Log.d("jiman=111", response.body().toString())
//                        continuation.resume(true) // 작업 성공 시 true 반환
////                        continuation.resume(false) // 오류 확인용 false
//                    } else {
//                        Log.d("jiman=222", "Response body is null")
//                        continuation.resume(false) // 작업 실패 시 false 반환
//                    }
//                } else {
//                    Log.d("jiman=333", "Failure")
//                    continuation.resume(false) // 작업 실패 시 false 반환
//                }
//            }
//
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
//                continuation.resume(false) // 작업 실패 시 false 반환
//            }
//        })
//    }
//}
//
//
