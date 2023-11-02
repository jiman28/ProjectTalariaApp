package com.example.projecttravel.ui.screens.login.api

import android.util.Log
import com.example.projecttravel.data.uistates.UserUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelUser
import com.example.projecttravel.data.RetrofitBuilderGetMap
import com.example.projecttravel.model.User
import com.example.projecttravel.model.UserResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
/** function for getting Weather ====================*/
suspend fun loginApiCall(
    user: User,
    userUiState: UserUiState,
    userViewModel: ViewModelUser,
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderGetMap.travelGetMapApiService.checkLogin(user)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        userViewModel.setUser(loginResponse)
                        Log.d("xxxxx1xxxxxxxxxxxxxxx", "Request Success + Response Success")
                        Log.d("xxxxx1xxxxxxxxxxxxxxx", call.toString())
                        Log.d("xxxxx1111111xxxxxxxxxxxxxxx", response.body().toString())
                        Log.d("xxxxx1222221xxxxxxxxxxxxxxx", userUiState.currentLogin.toString())
                        continuation.resume(true) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("xx2xxxxxxxxxxxxxxxxxx", "Response body is null")
                        Log.d("xx2xxxxxxxxxxxxxxxxxx", "Response body is null")
                        Log.d("xx2xxxxxxxxxxxxxxxxxx", "Response body is null")
                        continuation.resume(false) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("xx3xxxxxxxxxxxxxxxxxx", "Failure")
                    Log.d("xx3xxxxxxxxxxxxxxxxxx", "Failure")
                    Log.d("xx3xxxxxxxxxxxxxxxxxx", "Failure")
                    continuation.resume(false) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("xx4xxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
                Log.d("xx4xxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
                Log.d("xx4xxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
                continuation.resume(false) // 작업 실패 시 false 반환
            }
        })
    }
}
