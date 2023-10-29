package com.example.projecttravel.ui.screens.boards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.projecttravel.R
import com.example.projecttravel.data.uistates.PlanUiState
import com.example.projecttravel.ui.screens.login.data.UserUiState

@Composable
fun AllBoardsPage(
    userUiState: UserUiState,
    planUiState: PlanUiState,
    onBackButtonClicked: () -> Unit = {},
) {
    var selectedBoard by remember { mutableStateOf("board") }

    Column {
        /** Buttons ====================*/
        Column {
            BoardsPageButtons(
                userUiState = userUiState,
                planUiState = planUiState,
                onBoardListClicked = { selectedBoard = "board" },
                onCompanyListClicked = { selectedBoard = "company" },
                onTradeListClicked = { selectedBoard = "trade" },
                onReplyListClicked = { selectedBoard = "reply" },
            )
        }

        Divider(thickness = dimensionResource(R.dimen.thickness_divider3))

        /** Lists Show ====================*/
        Column {
            when (selectedBoard) {
                "board" -> {
                    BoardList(
                        contentPadding = PaddingValues(0.dp),
                    )
                }

                "company" -> {
                    CompanyList(
                        contentPadding = PaddingValues(0.dp),
                    )
                }

                "trade" -> {
                    TradeList(
                        contentPadding = PaddingValues(0.dp),
                    )
                }

                else -> {
                    CheckReplyList(
                        contentPadding = PaddingValues(0.dp),
                    )
                }
            }
        }
    }
}
