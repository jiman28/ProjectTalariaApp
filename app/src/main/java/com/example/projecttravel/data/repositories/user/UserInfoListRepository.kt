package com.example.projecttravel.data.repositories.user

import com.example.projecttravel.model.board.Reply
import com.example.projecttravel.model.user.UserInfo
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface UserInfoListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getUserInfoList(): List<UserInfo>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultUserInfoListRepository(
    private val travelApiService: TravelApiService
) : UserInfoListRepository {
    /** Retrieves list of Board from underlying data source */
    override suspend fun getUserInfoList(): List<UserInfo> = travelApiService.getUserInfoList()
}
