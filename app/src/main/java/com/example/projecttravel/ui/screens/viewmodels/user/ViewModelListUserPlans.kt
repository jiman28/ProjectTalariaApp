package com.example.projecttravel.ui.screens.viewmodels.user

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
import com.example.projecttravel.data.repositories.user.UserPlanListRepository
import com.example.projecttravel.model.PlansDataRead
import kotlinx.coroutines.launch

sealed interface PlanListUiState {
    data class PlanListSuccess(val planList: List<PlansDataRead>) : PlanListUiState
//    object Error : PlanListUiState
//    object Loading : PlanListUiState
}

class ViewModelListPlan(private val userPlanListRepository: UserPlanListRepository) : ViewModel() {

    var planListUiState: PlanListUiState by mutableStateOf(PlanListUiState.PlanListSuccess(emptyList()))
        private set

    init {
        getPlanList()
    }

    fun getPlanList() {
        viewModelScope.launch {
            try {
                val planList = userPlanListRepository.getUserPlansList()
                planListUiState = PlanListUiState.PlanListSuccess(planList)
                Log.d("hippolog", "$planList")
            } catch (e: Exception) {
                // Handle the error case if necessary
                Log.d("hippolog", "${e.message}")
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
        val PlanListFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val boardRepository = application.container.userPlanListRepository
                ViewModelListPlan(userPlanListRepository = boardRepository)
            }
        }
    }
}
