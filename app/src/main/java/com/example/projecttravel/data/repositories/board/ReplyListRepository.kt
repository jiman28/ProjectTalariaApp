package com.example.projecttravel.data.repositories.board

import com.example.projecttravel.model.board.Reply
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface ReplyListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getReplyList(): List<Reply>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultReplyListRepository(
    private val travelApiService: TravelApiService
) : ReplyListRepository {
    /** Retrieves list of Board from underlying data source */
    override suspend fun getReplyList(): List<Reply> = travelApiService.getReplyList()
}
