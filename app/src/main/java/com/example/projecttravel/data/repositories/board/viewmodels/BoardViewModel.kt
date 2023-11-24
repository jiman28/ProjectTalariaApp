package com.example.projecttravel.data.repositories.board.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projecttravel.R
import com.example.projecttravel.TravelApplication
import com.example.projecttravel.data.repositories.board.BoardRepository
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.model.AllBoardsEntity
import com.example.projecttravel.model.Board
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import com.example.projecttravel.model.CallReply
import com.example.projecttravel.model.Company
import com.example.projecttravel.model.CompanyList
import com.example.projecttravel.model.ReplyList
import com.example.projecttravel.model.Trade
import com.example.projecttravel.model.TradeList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BoardListUiState {
    data class Success(val boardList: BoardList?) : BoardListUiState
    object Error : BoardListUiState
    object Loading : BoardListUiState
}

sealed interface CompanyListUiState {
    data class Success(val companyList: CompanyList?) : CompanyListUiState
    object Error : CompanyListUiState
    object Loading : CompanyListUiState
}

sealed interface TradeListUiState {
    data class Success(val tradeList: TradeList?) : TradeListUiState, ReplyListUiState
    object Error : TradeListUiState
    object Loading : TradeListUiState
}

sealed interface ReplyListUiState {
    data class Success(val replyList: List<ReplyList>) : ReplyListUiState
    object Error : ReplyListUiState
    object Loading : ReplyListUiState
}

data class BoardUiState(
    /** for Search ============================== */
    val currentSearchKeyWord: String = "",
    val currentSearchType: Int = R.string.selectSearchType,
    val currentSearchUser: String = "",

    val currentBoardPage: Int = 0,
    val currentCompanyPage: Int = 0,
    val currentTradePage: Int = 0,

    /** for View Article ============================== */
    val currentSelectedBoardTab: Int = R.string.boardTabTitle,
    val currentSelectedArtcNum: String = "",
    val selectedViewBoard: AllBoardsEntity? = null,

    /** for ~~ ============================== */
    /** for ~~ ============================== */
    /** for ~~ ============================== */

    val selectedWriteBoardMenu: Int = R.string.selectTabTitle,

    val currentBoardList: BoardList? = null,
    val currentCompanyList: CompanyList? = null,
    val currentTradeList: TradeList? = null,

    val currentReplyList: List<ReplyList> = emptyList(),

    // for MyPage
    val myBoardContent: Board? = null,
    val myCompanyContent: Company? = null,
    val myTradeContent: Trade? = null,
)

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    var boardListUiState: BoardListUiState by mutableStateOf(BoardListUiState.Success(null))
        private set

    var companyListUiState: CompanyListUiState by mutableStateOf(CompanyListUiState.Success(null))
        private set

    var tradeListUiState: TradeListUiState by mutableStateOf(TradeListUiState.Success(null))
        private set

    var replyListUiState: ReplyListUiState by mutableStateOf(ReplyListUiState.Success(emptyList()))
        private set

    /** all selection */
    private val _boardUiState = MutableStateFlow(BoardUiState())
    val boardUiState: StateFlow<BoardUiState> = _boardUiState.asStateFlow()

//    init {
//        getBoard(callBoard: CallBoard)
//    }

    /** 통신 */
    // 리뷰
    fun getBoardList(callBoard: CallBoard) {
        viewModelScope.launch {
            boardListUiState = BoardListUiState.Loading
            boardListUiState = try {
                BoardListUiState.Success(boardRepository.getBoardList(callBoard))
            } catch (e: IOException) {
                Log.d("jimanLog=getBoardList", "${e.message}")
                BoardListUiState.Error
            } catch (e: HttpException) {
                Log.d("jimanLog=getBoardList", "${e.message}")
                BoardListUiState.Error
            }
        }
    }

    // 동행인
    fun getCompanyList(callBoard: CallBoard) {
        viewModelScope.launch {
            companyListUiState = CompanyListUiState.Loading
            companyListUiState = try {
                CompanyListUiState.Success(boardRepository.getCompanyList(callBoard))
            } catch (e: IOException) {
                Log.d("jimanLog=getCompanyList", "${e.message}")
                CompanyListUiState.Error
            } catch (e: HttpException) {
                Log.d("jimanLog=getCompanyList", "${e.message}")
                CompanyListUiState.Error
            }
        }
    }

    // 거래
    fun getTradeList(callBoard: CallBoard) {
        viewModelScope.launch {
            tradeListUiState = TradeListUiState.Loading
            tradeListUiState = try {
                TradeListUiState.Success(boardRepository.getTradeList(callBoard))
            } catch (e: IOException) {
                Log.d("jimanLog=getTradeList", "${e.message}")
                TradeListUiState.Error
            } catch (e: HttpException) {
                Log.d("jimanLog=getTradeList", "${e.message}")
                TradeListUiState.Error
            }
        }
    }
    
    // 댓글
    fun getReplyList(callReply: CallReply) {
        viewModelScope.launch {
            replyListUiState = ReplyListUiState.Loading
            replyListUiState = try {
                ReplyListUiState.Success(boardRepository.getReplyList(callReply))
            } catch (e: IOException) {
                Log.d("jimanLog=getTradeList", "${e.message}")
                ReplyListUiState.Error
            } catch (e: HttpException) {
                Log.d("jimanLog=getTradeList", "${e.message}")
                ReplyListUiState.Error
            }
        }
    }

    /** 내부 데이터 처리 */

    /** for Search ============================== */
    fun setSearchKeyWord(kw: String) {
        _boardUiState.update { currentState ->
            currentState.copy(currentSearchKeyWord = kw)
        }
    }

    fun setSearchType(desiredBoard: Int) {
        _boardUiState.update { currentState ->
            currentState.copy(currentSearchType = desiredBoard)
        }
    }

    fun setSearchUser(email: String) {
        _boardUiState.update { currentState ->
            currentState.copy(currentSearchUser = email)
        }
    }

    fun setBoardPage(page: Int) {
        _boardUiState.update { currentState ->
            currentState.copy(currentBoardPage = page)
        }
    }

    fun setCompanyPage(page: Int) {
        _boardUiState.update { currentState ->
            currentState.copy(currentCompanyPage = page)
        }
    }

    fun setTradePage(page: Int) {
        _boardUiState.update { currentState ->
            currentState.copy(currentTradePage = page)
        }
    }

    /** for View Article ============================== */
    fun setBoardTab(tab: Int) {
        _boardUiState.update { currentState ->
            currentState.copy(currentSelectedBoardTab = tab)
        }
    }

    fun setSelectedArtcNum(desiredBoard: String) {
        _boardUiState.update { currentState ->
            currentState.copy(currentSelectedArtcNum = desiredBoard)
        }
    }

    fun setViewBoard(viewBoard: AllBoardsEntity) {
        _boardUiState.update { currentState ->
            currentState.copy(selectedViewBoard = viewBoard)
        }
    }

    /** for ~~ ============================== */
    /** for ~~ ============================== */
    /** for ~~ ============================== */

    fun setWriteBoardMenu(desiredBoard: Int) {
        _boardUiState.update { currentState ->
            currentState.copy(selectedWriteBoardMenu = desiredBoard)
        }
    }

    fun setBoardList(lists: BoardList) {
        _boardUiState.update { currentState ->
            currentState.copy(currentBoardList = lists)
        }
    }

    fun setCompanyList(lists: CompanyList) {
        _boardUiState.update { currentState ->
            currentState.copy(currentCompanyList = lists)
        }
    }

    fun setTradeList(lists: TradeList) {
        _boardUiState.update { currentState ->
            currentState.copy(currentTradeList = lists)
        }
    }

    fun setReplyList(lists: List<ReplyList>) {
        _boardUiState.update { currentState ->
            currentState.copy(currentReplyList = lists)
        }
    }

    // for MyPage=====================================

    fun setMyBoard(desiredBoard: Board) {
        _boardUiState.update { currentState ->
            currentState.copy(myBoardContent = desiredBoard)
        }
    }

    fun setMyCompany(desiredBoard: Company) {
        _boardUiState.update { currentState ->
            currentState.copy(myCompanyContent = desiredBoard)
        }
    }

    fun setMyTrade(desiredBoard: Trade) {
        _boardUiState.update { currentState ->
            currentState.copy(myTradeContent = desiredBoard)
        }
    }

    /** etc ============================== */
    /** reset all Objects */
    fun resetBoardPage() {
        _boardUiState.value = BoardUiState() // UserUiState를 기본 값(null)으로 재설정
    }


    /** viewModelFactory */
    companion object {
        val BoardFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val boardRepository = application.container.boardRepository
                BoardViewModel(boardRepository = boardRepository)
            }
        }
    }
}
