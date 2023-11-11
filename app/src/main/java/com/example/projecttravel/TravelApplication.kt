package com.example.projecttravel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.example.projecttravel.data.AppContainer
import com.example.projecttravel.data.DefaultAppContainer
import com.example.projecttravel.data.uistates.BoardPageUiState
import com.example.projecttravel.data.uistates.viewmodels.BoardPageViewModel

class TravelApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
//    var boardPageUiState = mutableStateOf(BoardPageUiState())
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
//        val boardPageViewModel = BoardPageViewModel(boardPageUiState)
//        container = DefaultAppContainer(resources, boardPageViewModel)
    }
}
