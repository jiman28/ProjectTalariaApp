package com.example.projecttravel.model.select

import kotlinx.serialization.Serializable

@Serializable
data class InterestInfo (
    val interestId: String,
    val interestType: String,
    val interestImg: String,
    // 다음에 이 데이터가 안 뽑히면 jar 파일에서 카멜표기법으로 제대로 변환시켜서 그런거임
)