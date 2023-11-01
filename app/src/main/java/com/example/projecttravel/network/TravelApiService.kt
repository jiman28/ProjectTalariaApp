package com.example.projecttravel.network

import com.example.projecttravel.model.board.Board
import com.example.projecttravel.model.board.Company
import com.example.projecttravel.model.board.Reply
import com.example.projecttravel.model.board.Trade
import com.example.projecttravel.model.select.CityInfo
import com.example.projecttravel.model.select.CountryInfo
import com.example.projecttravel.model.select.InterestInfo
import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.model.select.TourAttractionInfo
import com.example.projecttravel.zdump.dtsample.Form
import com.example.projecttravel.ui.screens.login.data.User
import com.example.projecttravel.ui.screens.login.data.UserResponse
import com.example.projecttravel.ui.screens.selection.selectapi.GetAttrWeather
import com.example.projecttravel.model.plan.SpotDtoResponse
import com.example.projecttravel.model.plan.WeatherCallSend
import com.example.projecttravel.model.plan.WeatherResponseGet
import com.example.projecttravel.model.user.UserInfo
import com.example.projecttravel.ui.screens.boards.boardapi.RemoveComment
import com.example.projecttravel.ui.screens.boards.boardapi.SendComment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TravelApiService {
    // User DB Calls ==============================
    // Login Calls
    @POST("androidlogin")
    fun checkLogin(@Body user: User): Call<UserResponse>

    // SignIn Call
    @POST("androidsignin")
    fun setFormResponse(@Body form: Form): Call<String>


    // UserInfo DB ==============================
    @GET("userinfo")
    suspend fun getUserInfoList(): List<UserInfo>


    // Travel DB ==============================
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


    // Travel DB Edit Calls ==============================
    // send placeName to searchedPlaceInfo
    @POST("sendplacename")
    @FormUrlEncoded
    fun setPlaceName(@Field("placeName") placeName: String?, @Field("cityId") cityId: String?,): Call<String>

    // send inout Info to DB
    @POST("sendinout")
    @FormUrlEncoded
    fun setInOut(@Field("placeName") placeName: String, @Field("stateInOut") stateInOut: String, ): Call<String>

    // send date to get WeatherInfos
    @POST("sendDate")
    fun getDateWeather(@Body weatherCallSend: WeatherCallSend): Call<List<WeatherResponseGet>>

    // send date to get AttrList by Weather
    @POST("sendAttrWeather")
    fun getDateAttr(@Body getAttrWeather: GetAttrWeather): Call<List<SpotDtoResponse>>

    // send date to get AttrList by City ~ OnWorking
    @POST("sendAttrCity")
    fun getDateCityAttr(@Body getAttrWeather: GetAttrWeather): Call<List<SpotDtoResponse>>


    // Board DB ==============================
    @GET("board")
    suspend fun getBoardList(): List<Board>

    @GET("company")
    suspend fun getCompanyList(): List<Company>

    @GET("trade")
    suspend fun getTradeList(): List<Trade>

    @GET("reply")
    suspend fun getReplyList(): List<Reply>


    // Board DB Edit Calls ==============================
    // viewCounter
    @POST("sendviewcount")
    @FormUrlEncoded
    fun setView(@Field("tabtitle") tabtitle: String, @Field("articleNo") articleNo: String, ): Call<String>

    // write comment,reply
    @POST("sendreply")
    fun sendReply(@Body sendComment: SendComment): Call<Boolean>

    // delete comment,reply
    @POST("removereply")
    fun removeReply(@Body removeComment: RemoveComment): Call<Boolean>

}

//    // TestBoardA
//    @POST("sendtba")
//    fun setTestBoardA(@Body testBoardASend: TestBoardASend): Call<String>