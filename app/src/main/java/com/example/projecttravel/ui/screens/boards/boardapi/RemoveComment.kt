package com.example.projecttravel.ui.screens.boards.boardapi

import kotlinx.serialization.Serializable

@Serializable
data class RemoveComment(
    val tabTitle: String,
    val articleNo: String,
    val replyNo: String,
)
