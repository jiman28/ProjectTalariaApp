package com.example.projecttravel.data.uistates

import com.example.projecttravel.model.SearchedPlace

data class SearchUiState(
    val errorMsg: String? = null,
    val searchedPlace: SearchedPlace? = null,
)
