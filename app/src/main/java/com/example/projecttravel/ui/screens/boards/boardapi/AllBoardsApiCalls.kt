package com.example.projecttravel.ui.screens.boards.boardapi

import android.util.Log
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
// get boardList
suspend fun getBoardListMobile (
    callBoard: CallBoard,
): BoardList? {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.callBoardList(callBoard)
        call.enqueue(object : Callback<BoardList> {
            override fun onResponse(
                call: Call<BoardList>,
                response: Response<BoardList>
            ) {
                if (response.isSuccessful) {
                    val boardResponse = response.body()
                    if (boardResponse != null) {
                        Log.d("jiman=111", "Request Success + Response Success")
                        Log.d("jiman=111", call.toString())
                        Log.d("jiman=111", response.body().toString())
                        continuation.resume(boardResponse) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("jiman=222", "Response body is null")
                        continuation.resume(null) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("jiman=333", "Failure")
                    Log.d("jiman=333", "Failure: ${response.errorBody()?.string()}")
                    continuation.resume(null) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<BoardList>, t: Throwable) {
                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
                continuation.resume(null) // 작업 실패 시 false 반환
            }
        })
    }
}
