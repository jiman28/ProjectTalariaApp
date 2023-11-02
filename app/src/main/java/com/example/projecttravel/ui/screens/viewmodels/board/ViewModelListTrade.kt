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
import com.example.projecttravel.data.repositories.board.TradeListRepository
import com.example.projecttravel.model.Trade
import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException

sealed interface TradeUiState {
    data class TradeSuccess(val tradeList: List<Trade>) : TradeUiState
//    object Error : TradeUiState
//    object Loading : TradeUiState
}

class ViewModelListTrade(private val tradeListRepository: TradeListRepository) : ViewModel() {

    var tradeUiState: TradeUiState by mutableStateOf(TradeUiState.TradeSuccess(emptyList()))
        private set

    init {
        getTrade()
    }

    fun getTrade() {
        viewModelScope.launch {
            try {
                val tradeList = tradeListRepository.getTradeList()
                tradeUiState = TradeUiState.TradeSuccess(tradeList)
            } catch (e: Exception) {
                // Handle the error case if necessary
            }
        }
    }

//    fun getTrade() {
//        viewModelScope.launch {
//            tradeUiState = TradeUiState.Loading
//            tradeUiState = try {
//                TradeUiState.TradeSuccess(tradeListRepository.getTradeList())
//            } catch (e: IOException) {
//                TradeUiState.Error
//            } catch (e: HttpException) {
//                TradeUiState.Error
//            }
//        }
//    }

    companion object {
        val TradeFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val tradeRepository = application.container.tradeListRepository
                ViewModelListTrade(tradeListRepository = tradeRepository)
            }
        }
    }
}
