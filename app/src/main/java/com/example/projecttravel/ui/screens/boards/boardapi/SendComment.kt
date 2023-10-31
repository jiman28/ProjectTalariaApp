package com.example.projecttravel.ui.screens.boards.boardapi

import kotlinx.serialization.Serializable

@Serializable
data class SendComment (
    val tabTitle: String,
    val articleNo: String,
    val replyContent: String,
    val email: String,
)