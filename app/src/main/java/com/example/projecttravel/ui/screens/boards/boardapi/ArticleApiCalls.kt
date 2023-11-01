package com.example.projecttravel.ui.screens.boards.boardapi

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.projecttravel.data.RetrofitBuilderString
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** 텍스트를 일정 길이로 자르고 "..."을 추가하는 Composable 함수 */
@Composable
fun EllipsisTextBoard(
    text: String,
    maxLength: Int,
    modifier: Modifier = Modifier
) {
    val displayText = if (text.length > maxLength) {
        text.substring(0, maxLength) + "..."
    } else {
        text
    }
    Text(text = displayText, fontSize = 20.sp, modifier = modifier)
}

/** ======================================================================================== */
/** synchronous codes ===================================================================== */
// update view count
fun viewCounter (
    tabtitle: String,
    articleNo: String,
){
    val call = RetrofitBuilderString.travelStringApiService.setView(tabtitle = tabtitle, articleNo = articleNo)
    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                // 통신 및 응답 성공
                Log.d("YYYYYYYYYYYYYYYYYYYY", "Request Success + Response Success")
                Log.d("YYYYYYYYYYYYYYYYYYYY", call.toString())
                Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
            } else {
                // 통신은 성공하였으나 응답 실패
                Log.d("YYYYYYYYYYYYYYYYYYYY", "Request Success + Response FAILURE")
                Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
            }
        }
        override fun onFailure(call: Call<String>, t: Throwable) {
            // 통신 실패
            Log.d("YYYYYYYYYYYYYYYYYYYY", "Request FAILURE")
            Log.d("YYYYYYYYYYYYYYYYYYYY", "checking: $call")
        }
    })
}