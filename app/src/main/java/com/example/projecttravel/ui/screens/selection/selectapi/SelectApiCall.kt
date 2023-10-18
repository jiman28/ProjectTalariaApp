package com.example.projecttravel.ui.screens.selection.selectapi

import android.util.Log
import com.example.projecttravel.data.RetrofitBuilderGetMap
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "AAAAA"

/** ===================================================================== */
/** function for getting Weather ====================*/
fun getDateToWeather(
    selectUiState: SelectUiState,
    planViewModel: ViewModelPlan,
) {
    val firstCity = selectUiState.selectTourAttractions.first()
    val weatherCallSend = WeatherCallSend(
        startDate = selectUiState.selectDateRange?.start.toString(),
        endDate = selectUiState.selectDateRange?.endInclusive.toString(),
        lat = when (firstCity) {
            is TourAttractionSearchInfo -> firstCity.lat
            is TourAttractionInfo -> firstCity.lat
            else -> { "몰루" } },
        lng = when (firstCity) {
            is TourAttractionSearchInfo -> firstCity.lng
            is TourAttractionInfo -> firstCity.lan
            else -> { "몰루" } },
    )
    val call = RetrofitBuilderJson.travelJsonApiService.getDateWeather(
        weatherCallSend
    )

    call.enqueue(object : Callback<List<WeatherResponseGet>> { // WeatherResponseGet으로 수정
        override fun onResponse(
            call: Call<List<WeatherResponseGet>>,
            response: Response<List<WeatherResponseGet>>,
        ) {
            if (response.isSuccessful) { // 응답이 성공인 경우
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    planViewModel.setDateToWeather(weatherResponse)
                    // 날씨 정보를 처리하거나 출력하는 코드를 작성
                    Log.d(TAG, weatherResponse.toString())
                } else {
                    // 응답은 성공했지만 내용이 null인 경우 처리
                    Log.d(TAG, "Response body is null")
                }
            } else {
                // 응답이 실패한 경우
                Log.d(TAG, "Failure")
            }
        }
        override fun onFailure(call: Call<List<WeatherResponseGet>>, t: Throwable) {
            t.localizedMessage?.let { Log.d(TAG, it) }
        }
    }
    )
}

/** ===================================================================== */
/** function for getting Weather ====================*/
fun getDateToAttrByWeather(
    selectUiState: SelectUiState,
    planViewModel: ViewModelPlan,
) {
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
                }
                Log.d(TAG, "Request Success + Response Success")
                Log.d("YYYYYYYYYYYYYYYYYYYY", call.toString())
                Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
            } else {
                // 통신은 성공하였으나 응답 실패
                Log.d(TAG, "Request Success + Response FAILURE")
                Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
            }
        }

        override fun onFailure(call: Call<List<SpotDtoResponse>>, t: Throwable) {
            // 통신 실패
            Log.d(TAG, "Request FAILURE")
            Log.d(TAG, "checking: $call")
        }
    })
}
