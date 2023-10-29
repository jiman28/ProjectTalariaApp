package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.ui.screens.viewmodels.board.CompanyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListCompany

@Composable
fun CompanyList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    val companyListViewModel: ViewModelListCompany =
        viewModel(factory = ViewModelListCompany.CompanyFactory)
    val companyUiState = (companyListViewModel.companyUiState as? CompanyUiState.CompanySuccess)
    if (companyUiState != null) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = companyUiState.companyList,
                key = { company ->
                    company.articleNo
                }
            ) { company ->
                Column {
                    Text(text = "articleNo = ${company.articleNo}")
                    Text(text = "content = ${company.content}")
                    Text(text = "title = ${company.title}")
                    Text(text = "views = ${company.views}")
                    Text(text = "writeDate = ${company.writeDate}")
                    Text(text = "writeId = ${company.writeId}")
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            }
        }
    }
}
