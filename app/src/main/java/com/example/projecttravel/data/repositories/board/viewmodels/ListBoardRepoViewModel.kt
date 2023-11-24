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
import com.example.projecttravel.TravelApplication
import com.example.projecttravel.data.repositories.board.BoardListRepository
import com.example.projecttravel.model.BoardList
import com.example.projecttravel.model.CallBoard
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BoardUiState {
    data class BoardSuccess(val boardList: BoardList?) : BoardUiState
    object Error : BoardUiState
    object Loading : BoardUiState
}

class ListBoardRepoViewModel(private val boardListRepository: BoardListRepository) : ViewModel() {

    var boardUiState: BoardUiState by mutableStateOf(BoardUiState.BoardSuccess(null))
        private set

//    init {
//        getBoard(callBoard: CallBoard)
//    }

    fun getBoard(callBoard: CallBoard) {
        viewModelScope.launch {
            boardUiState = BoardUiState.Loading
            boardUiState = try {
                BoardUiState.BoardSuccess(boardListRepository.getBoardList(callBoard))
            } catch (e: IOException) {
                Log.d("jimanLog=getBoardList", "${e.message}")
                BoardUiState.Error
            } catch (e: HttpException) {
                Log.d("jimanLog=getBoardList", "${e.message}")
                BoardUiState.Error
            }
        }
    }

//    companion object {
//        val BoardFactory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
//                        as TravelApplication)
//                val boardRepository = application.container.boardListRepository
//                ListBoardRepoViewModel(boardListRepository = boardRepository)
//            }
//        }
//    }
}
