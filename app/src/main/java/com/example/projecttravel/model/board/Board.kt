package com.example.projecttravel.model.board

import kotlinx.serialization.Serializable

@Serializable
data class Board (
    val articleNo: String,
    val title: String,
    val content: String,
    val writeDate: String,
    val views: String,
    val writeId: String,
    val userId: String,
): AllBoards
