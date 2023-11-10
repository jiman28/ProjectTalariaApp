//package com.example.projecttravel.ui.screens.boardview.boardapi
//
//import android.util.Log
//import com.example.projecttravel.data.RetrofitBuilderJson
//import com.example.projecttravel.model.RemoveComment
//import com.example.projecttravel.model.SendComment
//import kotlinx.coroutines.suspendCancellableCoroutine
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import kotlin.coroutines.resume
//
///** ======================================================================================== */
///** asynchronous codes ===================================================================== */
//// send Comment to DB
//suspend fun sendCommentToDb (
//    sendComment: SendComment,
//): Boolean {
//    return suspendCancellableCoroutine { continuation ->
//        val call = RetrofitBuilderJson.travelJsonApiService.sendReply(sendComment)
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
///** ======================================================================================== */
///** asynchronous codes ===================================================================== */
//// remove Comment From Db
//suspend fun removeCommentFromDb (
//    removeComment: RemoveComment,
//): Boolean {
//    return suspendCancellableCoroutine { continuation ->
//        val call = RetrofitBuilderJson.travelJsonApiService.removeReply(removeComment)
//        call.enqueue(object : Callback<Boolean> {
//            override fun onResponse(
//                call: Call<Boolean>,
//                response: Response<Boolean>
//            ) {
//                if (response.isSuccessful) {
//                    val loginResponse = response.body()
//                    if (loginResponse != null) {
//                        Log.d("xxxxx1xxxxxxxxxxxxxxx", "Request Success + Response Success")
//                        Log.d("xxxxx1xxxxxxxxxxxxxxx", call.toString())
//                        Log.d("xxxxx1111111xxxxxxxxxxxxxxx", response.body().toString())
//                        continuation.resume(true) // 작업 성공 시 true 반환
////                        continuation.resume(false) // 오류 확인용 false
//                    } else {
//                        Log.d("xx2xxxxxxxxxxxxxxxxxx", "Response body is null")
//                        Log.d("xx2xxxxxxxxxxxxxxxxxx", "Response body is null")
//                        Log.d("xx2xxxxxxxxxxxxxxxxxx", "Response body is null")
//                        continuation.resume(false) // 작업 실패 시 false 반환
//                    }
//                } else {
//                    Log.d("xx3xxxxxxxxxxxxxxxxxx", "Failure")
//                    Log.d("xx3xxxxxxxxxxxxxxxxxx", "Failure")
//                    Log.d("xx3xxxxxxxxxxxxxxxxxx", "Failure")
//                    continuation.resume(false) // 작업 실패 시 false 반환
//                }
//            }
//
//            override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                Log.d("xx4xxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
//                Log.d("xx4xxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
//                Log.d("xx4xxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
//                continuation.resume(false) // 작업 실패 시 false 반환
//            }
//        })
//    }
//}
