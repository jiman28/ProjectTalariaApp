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
import com.example.projecttravel.data.repositories.board.ReplyListRepository
import com.example.projecttravel.model.board.Reply
import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException

sealed interface ReplyUiState {
    data class ReplySuccess(val replyList: List<Reply>) : ReplyUiState
//    object Error : ReplyUiState
//    object Loading : ReplyUiState
}

class ViewModelListReply(private val replyListRepository: ReplyListRepository) : ViewModel() {

    var replyUiState: ReplyUiState by mutableStateOf(ReplyUiState.ReplySuccess(emptyList()))
        private set

    init {
        getReply()
    }

    fun getReply() {
        viewModelScope.launch {
            try {
                val replyList = replyListRepository.getReplyList()
                replyUiState = ReplyUiState.ReplySuccess(replyList)
            } catch (e: Exception) {
                // Handle the error case if necessary
            }
        }
    }

//    fun getReply() {
//        viewModelScope.launch {
//            replyUiState = ReplyUiState.Loading
//            replyUiState = try {
//                ReplyUiState.ReplySuccess(replyListRepository.getReplyList())
//            } catch (e: IOException) {
//                ReplyUiState.Error
//            } catch (e: HttpException) {
//                ReplyUiState.Error
//            }
//        }
//    }

    companion object {
        val ReplyFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val replyRepository = application.container.replyListRepository
                ViewModelListReply(replyListRepository = replyRepository)
            }
        }
    }
}
