package com.example.projecttravel.data.repositories.board

import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.model.CompanyList
import com.example.projecttravel.model.ReplyList
import com.example.projecttravel.model.TradeList
import com.example.projecttravel.network.TravelApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository retrieves Board data from underlying data source.
 */
interface BoardRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getBoardList(callBoard: CallBoard): BoardList
    suspend fun getCompanyList(callBoard: CallBoard): CompanyList
    suspend fun getTradeList(callBoard: CallBoard): TradeList
    suspend fun getReplyList(callReply: CallReply): List<ReplyList>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultBoardRepository(
    private val travelApiService: TravelApiService,
) : BoardRepository {

    override suspend fun getBoardList(callBoard: CallBoard): BoardList {
        return withContext(Dispatchers.IO) {
            travelApiService.callBoardList(callBoard).execute().body()
                ?: throw Exception("Unable to fetch board list")
        }
    }

    override suspend fun getCompanyList(callBoard: CallBoard): CompanyList {
        return withContext(Dispatchers.IO) {
            travelApiService.callCompanyList(callBoard).execute().body()
                ?: throw Exception("Unable to fetch board list")
        }
    }

    override suspend fun getTradeList(callBoard: CallBoard): TradeList {
        return withContext(Dispatchers.IO) {
            travelApiService.callTradeList(callBoard).execute().body()
                ?: throw Exception("Unable to fetch board list")
        }
    }

    override suspend fun getReplyList(callReply: CallReply): List<ReplyList> {
        return withContext(Dispatchers.IO) {
            travelApiService.callReplyList(callReply).execute().body()
                ?: throw Exception("Unable to fetch board list")
        }
    }
}


