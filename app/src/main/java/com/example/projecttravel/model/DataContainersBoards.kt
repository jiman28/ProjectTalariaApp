package com.example.projecttravel.model

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

interface AllBoards {
}

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

@Serializable
data class Company (
    val articleNo: String,
    val title: String,
    val content: String,
    val writeDate: String,
    val views: String,
    val writeId: String,
    val userId: String,
): AllBoards

@Serializable
data class Trade (
    val articleNo: String,
    val title: String,
    val content: String,
    val writeDate: String,
    val views: String,
    val writeId: String,
    val userId: String,
): AllBoards

@Serializable
data class Reply (
    val replyNo: String,
    val boardEntity: String,
    val tradeEntity: String,
    val companyEntity: String,
    val replyContent: String,
    val writeDate: String,
    val writeId: String,
    val userId: String,
)

// DataClass for Call
@Serializable
data class BoardList (
    val pages: Int,
    val list: List<Board>,
)

@Serializable
data class CompanyList (
    val pages: Int,
    val list: List<Company>,
)

@Serializable
data class TradeList (
    val pages: Int,
    val list: List<Trade>,
)