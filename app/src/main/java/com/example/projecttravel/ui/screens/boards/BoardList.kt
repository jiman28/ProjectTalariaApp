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
import com.example.projecttravel.ui.screens.viewmodels.board.BoardUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListBoard

@Composable
fun BoardList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    val boardListViewModel: ViewModelListBoard =
        viewModel(factory = ViewModelListBoard.BoardFactory)
    val boardUiState = (boardListViewModel.boardUiState as? BoardUiState.BoardSuccess)
    if (boardUiState != null) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = boardUiState.boardList,
                key = { board ->
                    board.articleNo
                }
            ) { board ->
                Column {
                    Text(text = "articleNo = ${board.articleNo}")
                    Text(text = "content = ${board.content}")
                    Text(text = "title = ${board.title}")
                    Text(text = "views = ${board.views}")
                    Text(text = "writeDate = ${board.writeDate}")
                    Text(text = "writeId = ${board.writeId}")
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            }
        }
    }
}
