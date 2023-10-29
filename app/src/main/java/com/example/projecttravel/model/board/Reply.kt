package com.example.projecttravel.model.board

import kotlinx.serialization.Serializable

@Serializable
data class Reply (
    val replyNo: String,
    val boardEntity: String,
    val tradeEntity: String,
    val companyEntity: String,
    val replycontent: String,
    val writeDate: String,
    val writeId: String,
    val userId: String,
)
