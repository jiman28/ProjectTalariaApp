package com.example.projecttravel.ui.screens.searchPlaceGps

import android.content.Context
import android.util.Log
import com.example.projecttravel.BuildConfig
import com.example.projecttravel.data.RetrofitBuilderString
import com.example.projecttravel.model.search.TourAttractionSearchInfo
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSearch
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesStatusCodes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG1 = "AAAAA"
private const val TAG2 = "BBBBB"

/** Function for finding placeInfos by placeId ====================*/
fun getPlaceInfo(
    placeId: String,
    context: Context,
    searchViewModel: ViewModelSearch,
    updateUiPageClicked: () -> Unit = {},
) {
    // GOOGLE_MAPS_API_KEY 키 초기화
    val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
    Places.initialize(context, apiKey)
    // Places API 초기화
    val placesClient = Places.createClient(context)

    // 받아올 필드 값들을 특정함.
    val placeFields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.LAT_LNG,
        Place.Field.ADDRESS,
    )

    // FetchPlaceRequest 객체 생성. placeId 와 필드 값을 넘겨준다
    val request = FetchPlaceRequest.newInstance(placeId, placeFields)

    // request 객체를
    placesClient.fetchPlace(request)
        .addOnSuccessListener { response: FetchPlaceResponse ->
            val place = response.place
            val name = place.name
            val latLng = place.latLng
            val formattedAddress = place.address
            val searchedPlace = SearchedPlace(name = name, address = formattedAddress, latLng = latLng)
            if (latLng != null) {
                Log.d("YYYYYYYYYlatLnglatLnglatLnglatLnglatLngYYYYYYYYYYY", latLng.toString())
            }
            sendName(searchedPlace, searchViewModel, updateUiPageClicked)
        }
        .addOnFailureListener { exception: Exception ->
            if (exception is ApiException) {
                Log.e(TAG1, "Place not found: ${exception.message}")
                val statusCode = exception.statusCode
                when (statusCode) {
                    // 상태 코드에 따른 처리
                    PlacesStatusCodes.API_NOT_CONNECTED -> {
                        Log.e(TAG1, "API_NOT_CONNECTED")
                    }
                    else -> {
                        Log.e(TAG1, "Unknown error")
                    }
                }
            }
        }
}

/** Function for sending placeName to request placeImg ====================*/
fun sendName(
    searchedPlace: SearchedPlace,
    searchViewModel: ViewModelSearch,
    updateUiPageClicked: () -> Unit = {},
) {
    val call = RetrofitBuilderString.travelStringApiService.setPlaceName(searchedPlace.name)
    call.enqueue(
        object : Callback<String> { // 비동기 방식 통신 메소드
            override fun onResponse(
                // 통신에 성공한 경우
                call: Call<String>,
                response: Response<String>,
            ) {
                if (response.isSuccessful) { // 응답 잘 받은 경우
                    searchViewModel.setErrorMsg(null)
                    searchViewModel.setSearched(searchedPlace)
                    updateUiPageClicked()
                    Log.d(TAG2, "Request Success + Response Success")
                    Log.d("YYYYYYYYYYYYYYYYYYYY", call.toString())
                    Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
                } else {
                    // 통신 성공 but 응답 실패
                    searchViewModel.setErrorMsg("ㅈㅅ염 다시 ㄱㄱ")
                    Log.d(TAG2, "Request Success + Response FAILURE")
                    Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
                    updateUiPageClicked()
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신에 실패한 경우
                searchViewModel.setErrorMsg("ㅈㅅ염 다시 ㄱㄱ")
                Log.d(TAG2, "Request FAILURE")
                Log.d(TAG2, "checking: $call")
                updateUiPageClicked()
            }
        }
    )
}

fun sendInOut(
    placeName: String?,
    stateInOut: String?
) {
    if (placeName != null && stateInOut != null) {
        val call = RetrofitBuilderString.travelStringApiService.setInOut(placeName = placeName, stateInOut = stateInOut)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // 통신 및 응답 성공
                    Log.d(TAG2, "Request Success + Response Success")
                    Log.d("YYYYYYYYYYYYYYYYYYYY", call.toString())
                    Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
                } else {
                    // 통신은 성공하였으나 응답 실패
                    Log.d(TAG2, "Request Success + Response FAILURE")
                    Log.d("YYYYYYYYYYYYYYYYYYYY", response.body().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신 실패
                Log.d(TAG2, "Request FAILURE")
                Log.d(TAG2, "checking: $call")
            }
        })
    } else {
        // placeName 또는 stateInOut이 null일 때 처리
        Log.d(TAG2, "placeName or stateInOut is null")
    }
}
/** Function for finding Searched Place from DB by name ====================*/
fun findSearchListByName(
    searchName: String?,
    tourAttrSearchList: List<TourAttractionSearchInfo>,
): TourAttractionSearchInfo? {
    val searched = tourAttrSearchList.find { it.name == searchName }
    return searched
}
