package com.example.projecttravel.data.uistates

import com.example.projecttravel.model.select.TourAttractionSearchInfo
import com.example.projecttravel.model.select.CityInfo
import com.example.projecttravel.model.select.CountryInfo
import com.example.projecttravel.model.select.InterestInfo
import com.example.projecttravel.model.select.TourAttractionAll
import java.time.LocalDate

data class SelectUiState(
    // 객체를 저장함
    val selectDateRange: ClosedRange<LocalDate>? = null,
    val selectCountry: CountryInfo? = null,
    val selectCity: CityInfo? = null,
    val selectInterest: InterestInfo? = null,
    val selectSearch: TourAttractionSearchInfo? = null,
    val selectTourAttractions: List<TourAttractionAll> = emptyList(), // 기본적으로 빈 리스트로 초기화
)
