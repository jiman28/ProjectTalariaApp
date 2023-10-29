package com.example.projecttravel.ui.screens.viewmodels.board

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
import com.example.projecttravel.model.board.Board
import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException

sealed interface BoardUiState {
    data class BoardSuccess(val boardList: List<Board>) : BoardUiState
//    object Error : BoardUiState
//    object Loading : BoardUiState
}

class ViewModelListBoard(private val boardListRepository: BoardListRepository) : ViewModel() {

    var boardUiState: BoardUiState by mutableStateOf(BoardUiState.BoardSuccess(emptyList()))
        private set

    init {
        getBoard()
    }

    fun getBoard() {
        viewModelScope.launch {
            try {
                val boardList = boardListRepository.getBoardList()
                boardUiState = BoardUiState.BoardSuccess(boardList)
            } catch (e: Exception) {
                // Handle the error case if necessary
            }
        }
    }

//    fun getBoard() {
//        viewModelScope.launch {
//            boardUiState = BoardUiState.Loading
//            boardUiState = try {
//                BoardUiState.BoardSuccess(boardListRepository.getBoardList())
//            } catch (e: IOException) {
//                BoardUiState.Error
//            } catch (e: HttpException) {
//                BoardUiState.Error
//            }
//        }
//    }

    companion object {
        val BoardFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val boardRepository = application.container.boardListRepository
                ViewModelListBoard(boardListRepository = boardRepository)
            }
        }
    }
}
