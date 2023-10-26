package com.example.projecttravel.auth.login.api

import android.util.Log
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.auth.login.data.User
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
/** function for getting Weather ====================*/
suspend fun LoginApiCall(
    user: User,
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.getLoginResponse(user)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        Log.d("xxxxxxxxxxxxxxxxxxxx", "Request Success + Response Success")
                        Log.d("xxxxxxxxxxxxxxxxxxxx", call.toString())
                        Log.d("xxxxxxxxxxxxxxxxxxxx", response.body().toString())
                        continuation.resume(true) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("xxxxxxxxxxxxxxxxxxxx", "Response body is null")
                        continuation.resume(false) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("xxxxxxxxxxxxxxxxxxxx", "Failure")
                    continuation.resume(false) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("xxxxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
                continuation.resume(false) // 작업 실패 시 false 반환
            }
        })
    }
}