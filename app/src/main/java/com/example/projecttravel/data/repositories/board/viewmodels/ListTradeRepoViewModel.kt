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
import com.example.projecttravel.data.repositories.board.TradeListRepository
import com.example.projecttravel.model.TradeList
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface TradeUiState {
    data class TradeSuccess(val tradeList: TradeList?) : TradeUiState, ReplyUiState
    object Error : TradeUiState
    object Loading : TradeUiState
}

class ListTradeRepoViewModel(private val tradeListRepository: TradeListRepository) : ViewModel() {

    var tradeUiState: TradeUiState by mutableStateOf(TradeUiState.TradeSuccess(null))
        private set

    init {
        getTrade()
    }

    fun getTrade() {
        viewModelScope.launch {
            tradeUiState = TradeUiState.Loading
            tradeUiState = try {
                TradeUiState.TradeSuccess(tradeListRepository.getTradeList())
            } catch (e: IOException) {
                Log.d("jimanLog=getTradeList", "${e.message}")
                TradeUiState.Error
            } catch (e: HttpException) {
                Log.d("jimanLog=getTradeList", "${e.message}")
                TradeUiState.Error
            }
        }
    }

//    companion object {
//        val TradeFactory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
//                        as TravelApplication)
//                val tradeRepository = application.container.tradeListRepository
//                ListTradeRepoViewModel(tradeListRepository = tradeRepository)
//            }
//        }
//    }
}
