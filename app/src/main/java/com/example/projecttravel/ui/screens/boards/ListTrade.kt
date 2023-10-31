package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.BoardSelectUiState
import com.example.projecttravel.ui.screens.boards.boardapi.viewCounter
import com.example.projecttravel.ui.screens.viewmodels.ViewModelBoardSelect
import com.example.projecttravel.ui.screens.viewmodels.board.TradeUiState
import com.example.projecttravel.ui.screens.viewmodels.board.ViewModelListTrade

@Composable
fun ListTrade(
    modifier: Modifier = Modifier,
    boardSelectUiState: BoardSelectUiState,
    boardSelectViewModel: ViewModelBoardSelect,
    onBoardClicked: () -> Unit,
    contentPadding: PaddingValues,
) {
    val tradeListViewModel: ViewModelListTrade =
        viewModel(factory = ViewModelListTrade.TradeFactory)
    val tradeUiState = (tradeListViewModel.tradeUiState as? TradeUiState.TradeSuccess)

    val tabtitle: String = stringResource(R.string.tradeTitle)

    if (tradeUiState != null) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            items(
                items = tradeUiState.tradeList.reversed(),
                key = { trade ->
                    trade.articleNo
                }
            ) { trade ->
                Card(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                        horizontalArrangement = Arrangement.Center, // 수평 가운데 정렬
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center, // 수직 가운데 정렬
                            horizontalAlignment = Alignment.CenterHorizontally, // 수평 가운데 정렬
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = trade.articleNo)
                        }
                        Column(
                            modifier = Modifier.weight(7f)
                        ) {
                            TextButton(
                                onClick = {
                                    viewCounter(tabtitle, trade.articleNo)
                                    onBoardClicked()
                                    boardSelectViewModel.setSelectedTrade(trade)
                                }
                            ) {
                                Text(fontSize = 20.sp, text = trade.title)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Account"
                                )
                                Spacer(modifier = Modifier.padding(1.dp))
                                Text(fontSize = 12.sp, text = trade.writeId)

                                Spacer(modifier = Modifier.padding(5.dp))

                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.AccessTime,
                                    contentDescription = "WriteDate"
                                )
                                Spacer(modifier = Modifier.padding(1.dp))
                                Text(fontSize = 12.sp, text = trade.writeDate)

                                Spacer(modifier = Modifier.padding(5.dp))

                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Filled.RemoveRedEye,
                                    contentDescription = "Views"
                                )
                                Spacer(modifier = Modifier.padding(1.dp))
                                Text(fontSize = 12.sp, text = trade.views)
                            }
                        }
                    }
                }
            }
        }
    } else {
        Text(text = "아직 아무도 글 안 썻다우")
    }
}
