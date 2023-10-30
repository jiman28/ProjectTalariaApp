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
import com.example.projecttravel.ui.screens.viewmodels.board.TradeUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListTrade

@Composable
fun ListTrade(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    val tradeListViewModel: ViewModelListTrade =
        viewModel(factory = ViewModelListTrade.TradeFactory)
    val tradeUiState = (tradeListViewModel.tradeUiState as? TradeUiState.TradeSuccess)
    if (tradeUiState != null) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = tradeUiState.tradeList,
                key = { trade ->
                    trade.articleNo
                }
            ) { trade ->
                Column {
                    Text(text = "articleNo = ${trade.articleNo}")
                    Text(text = "content = ${trade.content}")
                    Text(text = "title = ${trade.title}")
                    Text(text = "views = ${trade.views}")
                    Text(text = "writeDate = ${trade.writeDate}")
                    Text(text = "writeId = ${trade.writeId}")
                    Text(text = "userId = ${trade.userId}")
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            }
        }
    }
}
