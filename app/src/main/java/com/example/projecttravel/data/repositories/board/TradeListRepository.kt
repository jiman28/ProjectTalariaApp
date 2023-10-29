package com.example.projecttravel.data.repositories.board

import com.example.projecttravel.model.board.Trade
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface TradeListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getTradeList(): List<Trade>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultTradeListRepository(
    private val travelApiService: TravelApiService
) : TradeListRepository {
    /** Retrieves list of Board from underlying data source */
    override suspend fun getTradeList(): List<Trade> = travelApiService.getTradeList()
}
