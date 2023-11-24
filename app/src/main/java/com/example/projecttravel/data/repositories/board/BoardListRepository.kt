package com.example.projecttravel.data.repositories.board

import android.content.res.Resources
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.network.TravelApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

/**
 * Repository retrieves Board data from underlying data source.
 */
interface BoardListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getBoardList(callBoard: CallBoard): BoardList
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultBoardListRepository(
    private val travelApiService: TravelApiService,
) : BoardListRepository {

    override suspend fun getBoardList(callBoard: CallBoard): BoardList {
        return withContext(Dispatchers.IO) {
            travelApiService.callBoardList(callBoard).execute().body()
                ?: throw Exception("Unable to fetch board list")
        }
    }
}


