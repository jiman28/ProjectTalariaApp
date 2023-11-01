package com.example.projecttravel.ui.screens.boards.boardapi

import kotlinx.serialization.Serializable

@Serializable
data class SendArticle (
    val tabTitle: String,
    val articleNo: String,
    val replyNo: String,
)

@Serializable
data class RemoveArticle (
    val tabTitle: String,
    val articleNo: String,
)

@Serializable
data class SendComment (
    val tabTitle: String,
    val articleNo: String,
    val replyContent: String,
    val email: String,
)

@Serializable
data class RemoveComment(
    val tabTitle: String,
    val articleNo: String,
    val replyNo: String,
)
