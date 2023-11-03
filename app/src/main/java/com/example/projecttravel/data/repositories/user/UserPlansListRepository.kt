package com.example.projecttravel.data.repositories.user

import com.example.projecttravel.model.PlansDataRead
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface UserPlanListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getUserPlansList(): List<PlansDataRead>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultUserPlanListRepository(
    private val travelApiService: TravelApiService
) : UserPlanListRepository {
    /** Retrieves list of Board from underlying data source */
    override suspend fun getUserPlansList(): List<PlansDataRead> = travelApiService.getUserPlansDataList()
}
