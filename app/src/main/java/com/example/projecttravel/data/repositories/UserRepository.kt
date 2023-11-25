package com.example.projecttravel.data.repositories

import com.example.projecttravel.model.PlansDataRead
import com.example.projecttravel.model.UserInterest
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface UserRepository {
    /** Retrieves list of Board from underlying data source */




//    suspend fun getUserPlansList(): List<PlansDataRead>
//    suspend fun getUserInterestList(): List<UserInterest>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultUserRepository(
    private val travelApiService: TravelApiService,
    private val travelStringApiService: TravelApiService,
) : UserRepository {
    /** Retrieves list of Board from underlying data source */




//    override suspend fun getUserPlansList(): List<PlansDataRead> = travelApiService.getUserPlansDataList()
//    override suspend fun getUserInterestList(): List<UserInterest> = travelApiService.getUserInfoList()
}
