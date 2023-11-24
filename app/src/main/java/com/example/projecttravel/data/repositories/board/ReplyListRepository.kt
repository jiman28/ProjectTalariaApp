//package com.example.projecttravel.data.repositories.board
//
//import android.content.res.Resources
//import com.example.projecttravel.data.uistates.BoardPageUiState
//import com.example.projecttravel.model.CallReply
//import com.example.projecttravel.model.ReplyList
//import com.example.projecttravel.network.TravelApiService
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.withContext
//
///**
// * Repository retrieves Board data from underlying data source.
// */
//interface ReplyListRepository {
//    /** Retrieves list of Board from underlying data source */
//    suspend fun getReplyList(): List<ReplyList>
//}
//
///**
// * Network Implementation of repository that retrieves Country data from underlying data source.
// */
//class DefaultReplyListRepository(
//    private val travelApiService: TravelApiService,
//    boardPageUiState: StateFlow<BoardPageUiState>,
//    resources: Resources,
//) : ReplyListRepository {
//    private val callReply = CallReply(
//        tabtitle =  resources.getString(boardPageUiState.value.currentSelectedBoardTab),
//        articleNo = boardPageUiState.value.currentSelectedArtcNum
//    )
//    override suspend fun getReplyList(): List<ReplyList> {
//        return withContext(Dispatchers.IO) {
//            travelApiService.callReplyList(callReply).execute().body()
//                ?: throw Exception("Unable to fetch board list")
//        }
//    }
//}
