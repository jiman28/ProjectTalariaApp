package com.example.projecttravel.ui.screens.plantrip.planapi

import android.util.Log
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.model.PlansData
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
// save Plan To MongoDb
suspend fun savePlanToMongoDb (
    plansData: PlansData,
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.addplan(plansData)
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
