package com.example.projecttravel.ui.screens.viewmodels.homepage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projecttravel.TravelApplication
import com.example.projecttravel.data.repositories.select.CountryListRepository
import com.example.projecttravel.model.select.CountryInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/** UI state for the Home screen */
sealed interface TravelUiState {

    data class Success(val countryInfo: List<CountryInfo>) : TravelUiState
    object Error : TravelUiState
    object Loading : TravelUiState
}

/** ViewModel containing the app data and method to retrieve the data */
class TravelViewModel(private val countryListRepository: CountryListRepository) : ViewModel() {

    var travelUiState: TravelUiState by mutableStateOf(TravelUiState.Loading)
        private set

    init {
        getCountry()
    }

    fun getCountry() {
        viewModelScope.launch {
            travelUiState = TravelUiState.Loading
            travelUiState = try {
                TravelUiState.Success(countryListRepository.getCountryList())
            } catch (e: IOException) {
                TravelUiState.Error
            } catch (e: HttpException) {
                TravelUiState.Error
            }
        }
    }
    /** Factory for TravelViewModel that takes countryInfoRepository as a dependency */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as TravelApplication)
                val travelRepository = application.container.countryListRepository
                TravelViewModel(countryListRepository = travelRepository)
            }
        }
    }
}
