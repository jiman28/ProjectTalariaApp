package com.example.projecttravel.ui.screens.boardview.readapi

import android.util.Log
import com.example.projecttravel.data.RetrofitBuilderJson
import com.example.projecttravel.data.RetrofitBuilderString
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.model.CompanyList
import com.example.projecttravel.model.ReplyList
import com.example.projecttravel.model.TradeList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

suspend fun getAllBoardDefault(
    callBoard: CallBoard,
    boardPageViewModel: BoardPageViewModel,
    scope: CoroutineScope,
): Boolean {
    return suspendCancellableCoroutine { continuation ->
        try {
            scope.launch {
                val isBoardComplete = getBoardListMobile(callBoard)
                val isCompanyComplete = getCompanyListMobile(callBoard)
                val isTradeComplete = getTradeListMobile(callBoard)
                if (isBoardComplete != null && isCompanyComplete != null && isTradeComplete != null) {
                    boardPageViewModel.setBoardList(isBoardComplete)
                    boardPageViewModel.setCompanyList(isCompanyComplete)
                    boardPageViewModel.setTradeList(isTradeComplete)
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
//                val boardListGood = async { getBoardListMobile(callBoard) }
//                val companyListGood = async { getCompanyListMobile(callBoard) }
//                val tradeListGood = async { getTradeListMobile(callBoard) }
//
//                val isBoardComplete = boardListGood.await()
//                val isCompanyComplete = companyListGood.await()
//                val isTradeComplete = tradeListGood.await()
//                if (isBoardComplete != null && isCompanyComplete != null && isTradeComplete != null) {
//                    boardPageViewModel.setBoardList(isBoardComplete)
//                    boardPageViewModel.setCompanyList(isCompanyComplete)
//                    boardPageViewModel.setTradeList(isTradeComplete)
//                    continuation.resume(true)
//                } else {
//                    continuation.resume(false)
//                }
            }
        } catch (e: Exception) {
            Log.d("jimanLog=111", "${e.message}")
            continuation.resume(false)
        }
    }
}


/** ======================================================================================== */
/** asynchronous codes ===================================================================== */
// get boardList
suspend fun getBoardListMobile(
    callBoard: CallBoard,
): BoardList? {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.callBoardList(callBoard)
        call.enqueue(object : Callback<BoardList> {
            override fun onResponse(
                call: Call<BoardList>,
                response: Response<BoardList>,
            ) {
                if (response.isSuccessful) {
                    val boardResponse = response.body()
                    if (boardResponse != null) {
                        Log.d("jiman=111", "Request Success + Response Success")
                        Log.d("jiman=111", call.toString())
                        Log.d("jiman=111", response.body().toString())
                        continuation.resume(boardResponse) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("jiman=222", "Response body is null")
                        continuation.resume(null) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("jiman=333", "Failure")
                    Log.d("jiman=333", "Failure: ${response.errorBody()?.string()}")
                    continuation.resume(null) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<BoardList>, t: Throwable) {
                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
                continuation.resume(null) // 작업 실패 시 false 반환
            }
        })
    }
}

suspend fun getCompanyListMobile(
    callBoard: CallBoard,
): CompanyList? {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.callCompanyList(callBoard)
        call.enqueue(object : Callback<CompanyList> {
            override fun onResponse(
                call: Call<CompanyList>,
                response: Response<CompanyList>,
            ) {
                if (response.isSuccessful) {
                    val boardResponse = response.body()
                    if (boardResponse != null) {
                        Log.d("jiman=111", "Request Success + Response Success")
                        Log.d("jiman=111", call.toString())
                        Log.d("jiman=111", response.body().toString())
                        continuation.resume(boardResponse) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("jiman=222", "Response body is null")
                        continuation.resume(null) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("jiman=333", "Failure")
                    Log.d("jiman=333", "Failure: ${response.errorBody()?.string()}")
                    continuation.resume(null) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<CompanyList>, t: Throwable) {
                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
                continuation.resume(null) // 작업 실패 시 false 반환
            }
        })
    }
}

suspend fun getTradeListMobile(
    callBoard: CallBoard,
): TradeList? {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.callTradeList(callBoard)
        call.enqueue(object : Callback<TradeList> {
            override fun onResponse(
                call: Call<TradeList>,
                response: Response<TradeList>,
            ) {
                if (response.isSuccessful) {
                    val boardResponse = response.body()
                    if (boardResponse != null) {
                        Log.d("jiman=111", "Request Success + Response Success")
                        Log.d("jiman=111", call.toString())
                        Log.d("jiman=111", response.body().toString())
                        continuation.resume(boardResponse) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("jiman=222", "Response body is null")
                        continuation.resume(null) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("jiman=333", "Failure")
                    Log.d("jiman=333", "Failure: ${response.errorBody()?.string()}")
                    continuation.resume(null) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<TradeList>, t: Throwable) {
                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
                continuation.resume(null) // 작업 실패 시 false 반환
            }
        })
    }
}

//=======================
// get boardList
suspend fun getReplyListMobile(
    callReply: CallReply,
): List<ReplyList>? {
    return suspendCancellableCoroutine { continuation ->
        val call = RetrofitBuilderJson.travelJsonApiService.callReplyList(callReply)
        call.enqueue(object : Callback<List<ReplyList>> {
            override fun onResponse(
                call: Call<List<ReplyList>>,
                response: Response<List<ReplyList>>,
            ) {
                if (response.isSuccessful) {
                    val replyResponse = response.body()
                    if (replyResponse != null) {
                        Log.d("jiman=111", "Request Success + Response Success")
                        Log.d("jiman=111", call.toString())
                        Log.d("jiman=111", response.body().toString())
                        continuation.resume(replyResponse) // 작업 성공 시 true 반환
//                        continuation.resume(false) // 오류 확인용 false
                    } else {
                        Log.d("jiman=222", "Response body is null")
                        continuation.resume(null) // 작업 실패 시 false 반환
                    }
                } else {
                    Log.d("jiman=333", "Failure")
                    Log.d("jiman=333", "Failure: ${response.errorBody()?.string()}")
                    continuation.resume(null) // 작업 실패 시 false 반환
                }
            }

            override fun onFailure(call: Call<List<ReplyList>>, t: Throwable) {
                Log.d("jiman=444", t.localizedMessage ?: "Unknown error")
                continuation.resume(null) // 작업 실패 시 false 반환
            }
        })
    }
}

/** ======================================================================================== */
/** synchronous codes ===================================================================== */
// update view count
fun viewCounter (
    tabtitle: String,
    articleNo: String,
){
    val call = RetrofitBuilderString.travelStringApiService.setView(tabtitle = tabtitle, articleNo = articleNo)
    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                // 통신 및 응답 성공
                Log.d("jiman=111", "Request Success + Response Success")
                Log.d("jiman=111", call.toString())
                Log.d("jiman=111", response.body().toString())
            } else {
                // 통신은 성공하였으나 응답 실패
                Log.d("jiman=222", "Request Success + Response FAILURE")
                Log.d("jiman=222", response.body().toString())
            }
        }
        override fun onFailure(call: Call<String>, t: Throwable) {
            // 통신 실패
            Log.d("jiman=333", "Request FAILURE")
            Log.d("jiman=333", "checking: $call")
        }
    })
}

