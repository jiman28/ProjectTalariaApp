package com.example.projecttravel.data

import com.example.projecttravel.BuildConfig
import com.example.projecttravel.data.repositories.board.BoardRepository
import com.example.projecttravel.data.repositories.board.DefaultBoardRepository
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
import com.example.projecttravel.data.repositories.user.DefaultUserInfoListRepository
import com.example.projecttravel.data.repositories.user.DefaultUserPlanListRepository
import com.example.projecttravel.data.repositories.user.UserInfoListRepository
import com.example.projecttravel.data.repositories.user.UserPlanListRepository
import com.example.projecttravel.network.TravelApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/** localhost IPv4 BuildConfig for Retrofit */
private val BASE_URL = BuildConfig.BASE_URL

/** Dependency Injection container at the application level. */
interface AppContainer {
    val countryListRepository: CountryListRepository
    val cityListRepository: CityListRepository
    val interestListRepository: InterestListRepository
    val tourAttractionListRepository: TourAttractionListRepository
    val tourAttrSearchListRepository: TourAttrSearchListRepository
    val boardRepository: BoardRepository
    val userInfoListRepository: UserInfoListRepository
    val userPlanListRepository: UserPlanListRepository
}

/** Implementation for the Dependency Injection container at the application level.
 * Variables are initialized lazily and the same instance is shared across the whole app. */
class DefaultAppContainer(
) : AppContainer {

    /** Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter */
    private val retrofit: Retrofit = Retrofit.Builder()
//        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    /** Retrofit service object for creating api calls */
    private val retrofitService: TravelApiService by lazy {
        retrofit.create(TravelApiService::class.java)
    }

    /** DI implementation for all of each repository - Travel */
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

    /** DI implementation for all of each repository - Boards */
    override val boardRepository: BoardRepository by lazy {
        DefaultBoardRepository(retrofitService)
    }

    /** DI implementation for all of each repository - UserInfo */
    override val userInfoListRepository: UserInfoListRepository by lazy {
        DefaultUserInfoListRepository(retrofitService)
    }

    override val userPlanListRepository: UserPlanListRepository by lazy {
        DefaultUserPlanListRepository(retrofitService)
    }

}
/** object for transfer data to server as STRING */
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

/** object for transfer data to server as Map */
object RetrofitBuilderJson {
    var travelJsonApiService: TravelApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Gson 컨버터 사용
            .build()
        travelJsonApiService = retrofit.create(TravelApiService::class.java)
    }
}

/** Custom String ApiService (for timeout error -> edit server call time to 30 sec) */
object RetrofitBuilderStringCustom {
    var travelStringApiCustomService: TravelApiService

    init {
        val customHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 연결 timeout 시간 설정 (초 단위)
            .readTimeout(30, TimeUnit.SECONDS)    // 읽기 timeout 시간 설정 (초 단위)
            .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 timeout 시간 설정 (초 단위)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // 요청 보내는 API 서버 URL. /로 끝나야 함
            .client(customHttpClient) // 커스텀 OkHttpClient 사용 설정
            .addConverterFactory(ScalarsConverterFactory.create())  // String 등 처리시
            .build()

        travelStringApiCustomService = retrofit.create(TravelApiService::class.java)
    }
}

///** Custom Json ApiService (for timeout error -> edit server call time to 30 sec) */
//object RetrofitBuilderJsonCustom {
//    var travelJsonApiCustomService: TravelApiService
//
//    init {
//        val customHttpClient = OkHttpClient.Builder()
//            .connectTimeout(30, TimeUnit.SECONDS) // 연결 timeout 시간 설정 (초 단위)
//            .readTimeout(30, TimeUnit.SECONDS)    // 읽기 timeout 시간 설정 (초 단위)
//            .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 timeout 시간 설정 (초 단위)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL) // 요청 보내는 API 서버 URL. /로 끝나야 함
//            .client(customHttpClient) // 커스텀 OkHttpClient 사용 설정
//            .addConverterFactory(ScalarsConverterFactory.create())  // String 등 처리시
//            .build()
//
//        travelJsonApiCustomService = retrofit.create(TravelApiService::class.java)
//    }
//}
