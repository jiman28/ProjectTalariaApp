package com.example.projecttravel.data.uistates

import com.example.projecttravel.ui.screens.searchPlaceGps.SearchedPlace

data class SearchUiState(
    val errorMsg: String? = null,
    open val searchedPlace: SearchedPlace? = null,
)
