package com.example.projecttravel.data

import com.example.projecttravel.BuildConfig
import com.example.projecttravel.data.repositories.select.CityListRepository
import com.example.projecttravel.data.repositories.select.CountryListRepository
import com.example.projecttravel.data.repositories.select.DefaultCityListRepository
import com.example.projecttravel.data.repositories.select.DefaultCountryListRepository
import com.example.projecttravel.data.repositories.select.DefaultInterestListRepository
import com.example.projecttravel.data.repositories.select.DefaultTourAttrSearchListRepository
import com.example.projecttravel.data.repositories.select.DefaultTourAttractionListRepository
import com.example.projecttravel.data.repositories.select.InterestListRepository
import com.example.projecttravel.data.repositories.select.TourAttrSearchListRepository
import com.example.projecttravel.data.repositories.select.TourAttractionListRepository
import com.example.projecttravel.network.TravelApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/** localhost Bit Edu Center */
private val BASE_URL = BuildConfig.BASE_URL

/** Dependency Injection container at the application level. */
interface AppContainer {
    val countryListRepository: CountryListRepository
    val cityListRepository: CityListRepository
    val interestListRepository: InterestListRepository
    val tourAttractionListRepository: TourAttractionListRepository
    val tourAttrSearchListRepository: TourAttrSearchListRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    /** Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    /** Retrofit service object for creating api calls */
    private val retrofitService: TravelApiService by lazy {
        retrofit.create(TravelApiService::class.java)
    }

    /** DI implementation for all of each repository */

    override val countryListRepository: CountryListRepository by lazy {
        DefaultCountryListRepository(retrofitService)
    }

    override val cityListRepository: CityListRepository by lazy {
        DefaultCityListRepository(retrofitService)
    }

    override val interestListRepository: InterestListRepository by lazy {
        DefaultInterestListRepository(retrofitService)
    }

    override val tourAttractionListRepository: TourAttractionListRepository by lazy {
        DefaultTourAttractionListRepository(retrofitService)
    }

    override val tourAttrSearchListRepository: TourAttrSearchListRepository by lazy {
        DefaultTourAttrSearchListRepository(retrofitService)
    }
}

/**
 * object for transfer data to server as JSON
 */
object RetrofitBuilderJson {
    var travelJsonApiService: TravelApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // 요청 보내는 API 서버 url. /로 끝나야 함
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) // Gson을 역직렬화
            .build()
        travelJsonApiService = retrofit.create(TravelApiService::class.java)
    }
}

/**
 * object for transfer data to server as STRING
 */
object RetrofitBuilderString {
    var travelStringApiService: TravelApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // 요청 보내는 API 서버 url. /로 끝나야 함
            .addConverterFactory(ScalarsConverterFactory.create())  // String 등 처리시
            .build()
        travelStringApiService = retrofit.create(TravelApiService::class.java)
    }
}

/**
 * object for transfer data to server as STRING
 */
object RetrofitBuilderGetMap {
    var travelGetMapApiService: TravelApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 사용
            .build()
        travelGetMapApiService = retrofit.create(TravelApiService::class.java)
    }
}
