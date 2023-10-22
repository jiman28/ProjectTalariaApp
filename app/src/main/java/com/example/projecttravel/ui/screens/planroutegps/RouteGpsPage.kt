package com.example.projecttravel.ui.screens.planroutegps

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.data.uistates.SearchUiState
import com.example.projecttravel.data.uistates.SelectUiState
import com.example.projecttravel.ui.screens.viewmodels.ViewModelPlan
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSearch
import com.example.projecttravel.ui.screens.viewmodels.ViewModelSelect

@Composable
fun RouteGpsPage(
    modifier: Modifier = Modifier,
    planUiState: PlanUiState,
    onBackButtonClicked: () -> Unit = {},
) {
    Text(text = "경로페이지 임시")
}