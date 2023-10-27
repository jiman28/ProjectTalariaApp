package com.example.projecttravel.ui.screens.selection.selectapi

import android.util.Log
import com.example.projecttravel.data.RetrofitBuilderGetMap
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.plan.SpotDtoResponse
import com.example.projecttravel.model.plan.WeatherCallSend
import com.example.projecttravel.model.plan.WeatherResponseGet
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

private const val TAG = "AAAAA"

/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
/** function for getting Weather ====================*/
suspend fun getDateToWeather(
    selectUiState: SelectUiState,
    planViewModel: ViewModelPlan
): Boolean {
    val firstCity = selectUiState.selectTourAttractions.first()
    val weatherCallSend = WeatherCallSend(
        startDate = selectUiState.selectDateRange?.start.toString(),
        endDate = selectUiState.selectDateRange?.endInclusive.toString(),
        lat = when (firstCity) {
            is TourAttractionSearchInfo -> firstCity.lat
            is TourAttractionInfo -> firstCity.lat
            else -> "몰루"
        },
        lng = when (firstCity) {
            is TourAttractionSearchInfo -> firstCity.lng
            is TourAttractionInfo -> firstCity.lan
            else -> "몰루"
        }
    )

    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.getDateWeather(weatherCallSend)
        call.enqueue(object : Callback<List<WeatherResponseGet>> {
            override fun onResponse(
                call: Call<List<WeatherResponseGet>>,
                response: Response<List<WeatherResponseGet>>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        planViewModel.setDateToWeather(weatherResponse)
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

            override fun onFailure(call: Call<List<WeatherResponseGet>>, t: Throwable) {
                Log.d("xxxxxxxxxxxxxxxxxxxx", t.localizedMessage ?: "Unknown error")
                continuation.resume(false) // 작업 실패 시 false 반환
            }
        })
    }
}


/** function for getting Weather ====================*/
suspend fun getDateToAttrByWeather(
    selectUiState: SelectUiState,
    planViewModel: ViewModelPlan,
): Boolean {
    val selections = selectUiState.selectTourAttractions
    val placeId = selections
        .filterIsInstance<TourAttractionInfo>() // TourAttractionInfo 타입만 선택
        .joinToString(",") { it.placeId } // placeId 값으로 join
    val finds = selections
        .filterIsInstance<TourAttractionSearchInfo>() // TourAttractionSearchInfo 타입만 선택
        .joinToString(",") { it.name } // name 값으로 join

    val selectedDate = "${selectUiState.selectDateRange?.start.toString()} to ${selectUiState.selectDateRange?.endInclusive.toString()}"

    val getAttrWeather = GetAttrWeather(
        placeId = placeId,
        finds = finds,
        selectedDate = selectedDate,
    )
    val call = RetrofitBuilderGetMap.travelGetMapApiService.getDateAttr(
        getAttrWeather
    )
    return suspendCancellableCoroutine { continuation ->
        call.enqueue(object : Callback<List<SpotDtoResponse>> {
            override fun onResponse(
                call: Call<List<SpotDtoResponse>>,
                response: Response<List<SpotDtoResponse>>,
            ) {
                if (response.isSuccessful) {
                    // 통신 및 응답 성공
                    val attrResponse = response.body()
                    if (attrResponse != null) {
                        planViewModel.setDateToAttrByWeather(attrResponse)
                        Log.d("YYYYYYYYYYYYYYYYYYYY", "Request Success + Response Success")
                        Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
                        continuation.resume(true) // 작업 성공 시 true 반환
                    } else {
                        Log.d("YYYYYYYYYYYYYYYYYYYY", "Response body is null")
                        continuation.resume(false) // 작업 실패 시 false 반환
                    }
                } else {
                    // 통신은 성공하였으나 응답 실패
                    Log.d("YYYYYYYYYYYYYYYYYYYY", "Request Success + Response FAILURE")
                    Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
                    continuation.resume(false) // 작업 실패 시 false 반환
                }
            }
            override fun onFailure(call: Call<List<SpotDtoResponse>>, t: Throwable) {
                // 통신 실패
                Log.d("YYYYYYYYYYYYYYYYYYYY", "Request FAILURE")
                Log.d("YYYYYYYYYYYYYYYYYYYY", "checking: $call")
                continuation.resume(false) // 작업 실패 시 false 반환
            }
        })
    }
}
