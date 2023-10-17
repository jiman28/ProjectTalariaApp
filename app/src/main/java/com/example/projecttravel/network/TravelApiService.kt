package com.example.projecttravel.network

import com.example.projecttravel.model.select.CityInfo
import com.example.projecttravel.model.select.CountryInfo
import com.example.projecttravel.model.select.InterestInfo
import com.example.projecttravel.model.board.TestBoardASend
import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.ui.screens.selection.WeatherCallSend
import com.example.projecttravel.ui.screens.selection.WeatherResponseGet
import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TravelApiService {
    @GET("country")
    suspend fun getCountryList(): List<CountryInfo>

    @GET("city")
    suspend fun getCityList(): List<CityInfo>

    @GET("interest")
    suspend fun getInterestList(): List<InterestInfo>

    @GET("tourattraction")
    suspend fun getTourAttractionList(): List<TourAttractionInfo>

    @GET("searc")
    suspend fun getTourAttrSearchList(): List<TourAttractionSearchInfo>

    // send placeName to searchedPlaceInfo
    @POST("sendplacename")
    fun setPlaceName(@Body sendTourAttrName: String?): Call<String>

    // send inout
    @POST("sendinout")
    @FormUrlEncoded
    fun setInOut(@Field("placeName") placeName: String, @Field("stateInOut") stateInOut: String, ): Call<String>

    // send date
    @POST("sendDate")
    fun getDateWeather(@Body weatherCallSend: WeatherCallSend): Call<List<WeatherResponseGet>>

    // TestBoardA
    @POST("sendtba")
    fun setTestBoardA(@Body testBoardASend: TestBoardASend): Call<String>
}
