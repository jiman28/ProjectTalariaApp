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
import com.example.projecttravel.data.repositories.board.CompanyListRepository
import com.example.projecttravel.model.Company
import kotlinx.coroutines.launch
//import retrofit2.HttpException
//import java.io.IOException

sealed interface CompanyUiState {
    data class CompanySuccess(val companyList: List<Company>) : CompanyUiState
//    object Error : CompanyUiState
//    object Loading : CompanyUiState
}

class ListCompanyRepoViewModel(private val companyListRepository: CompanyListRepository) : ViewModel() {

    var companyUiState: CompanyUiState by mutableStateOf(CompanyUiState.CompanySuccess(emptyList()))
        private set

    init {
        getCompany()
    }

    fun getCompany() {
        viewModelScope.launch {
            try {
                val companyList = companyListRepository.getCompanyList()
                companyUiState = CompanyUiState.CompanySuccess(companyList)
            } catch (e: Exception) {
                // Handle the error case if necessary
            }
        }
    }

//    fun getCompany() {
//        viewModelScope.launch {
//            companyUiState = CompanyUiState.Loading
//            companyUiState = try {
//                CompanyUiState.CompanySuccess(companyListRepository.getCompanyList())
//            } catch (e: IOException) {
//                CompanyUiState.Error
//            } catch (e: HttpException) {
//                CompanyUiState.Error
//            }
//        }
//    }

    companion object {
        val CompanyFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val companyRepository = application.container.companyListRepository
                ListCompanyRepoViewModel(companyListRepository = companyRepository)
            }
        }
    }
}
