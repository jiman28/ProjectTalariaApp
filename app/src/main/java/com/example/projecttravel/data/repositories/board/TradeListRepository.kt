package com.example.projecttravel.data.repositories.board

import android.content.res.Resources
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.TradeList
import com.example.projecttravel.network.TravelApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

/**
 * Repository retrieves Board data from underlying data source.
 */
interface TradeListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getTradeList(): TradeList
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultTradeListRepository(
    private val travelApiService: TravelApiService,
    boardPageUiState: StateFlow<BoardPageUiState>,
    resources: Resources,
) : TradeListRepository {
    private val callBoard = CallBoard(
        kw = boardPageUiState.value.currentSearchKeyWord,
        page = boardPageUiState.value.currentBoardPage,
        type = resources.getString(boardPageUiState.value.currentSearchType),
        email = boardPageUiState.value.currentSearchUser
    )
    override suspend fun getTradeList(): TradeList {
        return withContext(Dispatchers.IO) {
            travelApiService.callTradeList(callBoard).execute().body()
                ?: throw Exception("Unable to fetch board list")
        }
    }
}

