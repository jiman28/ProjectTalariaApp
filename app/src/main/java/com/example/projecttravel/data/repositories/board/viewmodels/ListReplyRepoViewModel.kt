//package com.example.projecttravel.data.repositories.board.viewmodels
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.projecttravel.TravelApplication
//import com.example.projecttravel.data.repositories.board.ReplyListRepository
//import com.example.projecttravel.model.ReplyList
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException
//
//sealed interface ReplyUiState {
//    data class ReplySuccess(val replyList: List<ReplyList>) : ReplyUiState
//    object Error : ReplyUiState
//    object Loading : ReplyUiState
//}
//
//class ListReplyRepoViewModel(private val replyListRepository: ReplyListRepository) : ViewModel() {
//
//    var replyUiState: ReplyUiState by mutableStateOf(ReplyUiState.ReplySuccess(emptyList()))
//        private set
//
//    init {
//        getReply()
//    }
//
//    fun getReply() {
//        viewModelScope.launch {
//            replyUiState = ReplyUiState.Loading
//            replyUiState = try {
//                ReplyUiState.ReplySuccess(replyListRepository.getReplyList())
//            } catch (e: IOException) {
//                Log.d("jimanLog=getReplyList", "${e.message}")
//                ReplyUiState.Error
//            } catch (e: HttpException) {
//                Log.d("jimanLog=getReplyList", "${e.message}")
//                ReplyUiState.Error
//            }
//        }
//    }
//
////    companion object {
////        val ReplyFactory: ViewModelProvider.Factory = viewModelFactory {
////            initializer {
////                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
////                        as TravelApplication)
////                val replyRepository = application.container.replyListRepository
////                ListReplyRepoViewModel(replyListRepository = replyRepository)
////            }
////        }
////    }
//}
