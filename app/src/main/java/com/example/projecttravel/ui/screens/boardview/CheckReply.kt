package com.example.projecttravel.ui.screens.boardview

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
import com.example.projecttravel.ui.screens.viewmodels.board.ReplyUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ListReplyRepoViewModel

@Composable
fun CheckReply(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    val listReplyRepoViewModel: ListReplyRepoViewModel = viewModel(factory = ListReplyRepoViewModel.ReplyFactory)
    val replyUiState = (listReplyRepoViewModel.replyUiState as? ReplyUiState.ReplySuccess)
    if (replyUiState != null) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(
                items = replyUiState.replyList,
                key = { reply ->
                    reply.replyNo
                }
            ) { reply ->
                Column {
                    Text(text = "replyNo = ${reply.replyNo}")
                    Text(text = "boardEntity = ${reply.boardEntity}")
                    Text(text = "companyEntity = ${reply.companyEntity}")
                    Text(text = "tradeEntity = ${reply.tradeEntity}")
                    Text(text = "replycontent = ${reply.replyContent}")
                    Text(text = "writeDate = ${reply.writeDate}")
                    Text(text = "writeId = ${reply.writeId}")
                    Text(text = "userId = ${reply.userId}")
                }
                Divider(thickness = dimensionResource(R.dimen.thickness_divider3))
            }
        }
    }
}
