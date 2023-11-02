package com.example.projecttravel.data.repositories.board

import com.example.projecttravel.model.Board
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface BoardListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getBoardList(): List<Board>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultBoardListRepository(
    private val travelApiService: TravelApiService
) : BoardListRepository {
    /** Retrieves list of Board from underlying data source */
    override suspend fun getBoardList(): List<Board> = travelApiService.getBoardList()
}
