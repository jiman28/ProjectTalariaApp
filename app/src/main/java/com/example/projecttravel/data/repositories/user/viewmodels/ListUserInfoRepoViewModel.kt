package com.example.projecttravel.data.repositories.user.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projecttravel.TravelApplication
import com.example.projecttravel.data.repositories.user.UserInfoListRepository
import com.example.projecttravel.model.UserInterest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UserInfoUiState {
    data class UserInfoSuccess(val userInterestList: List<UserInterest>) : UserInfoUiState
    object Error : UserInfoUiState
    object Loading : UserInfoUiState
}

class ListUserInfoRepoViewModel(private val userInfoListRepository: UserInfoListRepository) : ViewModel() {

    var userInfoUiState: UserInfoUiState by mutableStateOf(UserInfoUiState.UserInfoSuccess(emptyList()))
        private set

    init {
        getUserInfo()
    }

//    fun getUserInfo() {
//        viewModelScope.launch {
//            try {
//                val userInfoList = userInfoListRepository.getUserInfoList()
//                userInfoUiState = UserInfoUiState.UserInfoSuccess(userInfoList)
//            } catch (e: Exception) {
//                // Handle the error case if necessary
//            }
//        }
//    }

    fun getUserInfo() {
        viewModelScope.launch {
            userInfoUiState = UserInfoUiState.Loading
            userInfoUiState = try {
                UserInfoUiState.UserInfoSuccess(userInfoListRepository.getUserInfoList())
            } catch (e: IOException) {
                UserInfoUiState.Error
            } catch (e: HttpException) {
                UserInfoUiState.Error
            }
        }
    }

    companion object {
        val UserInfoFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val userInfoRepository = application.container.userInfoListRepository
                ListUserInfoRepoViewModel(userInfoListRepository = userInfoRepository)
            }
        }
    }
}