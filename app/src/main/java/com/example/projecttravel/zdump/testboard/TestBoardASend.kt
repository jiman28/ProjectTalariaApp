package com.example.projecttravel.zdump.testboard

import kotlinx.serialization.Serializable

@Serializable
data class TestBoardASend(
    var boardAName: String,
    var boardAContent: String,
    var boardAAuthor: String,
)
