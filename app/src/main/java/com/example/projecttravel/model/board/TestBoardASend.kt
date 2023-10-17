package com.example.projecttravel.model.board

import kotlinx.serialization.Serializable

@Serializable
data class TestBoardASend(
    var boardAName: String,
    var boardAContent: String,
    var boardAAuthor: String,
)
