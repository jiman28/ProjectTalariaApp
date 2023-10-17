package com.example.projecttravel.model.select

import kotlinx.serialization.Serializable

@Serializable
data class CountryInfo (
    val countryId: String,
    val countryInfo: String,
    val countryName: String,
    val currency: String,
    val imageC: String,
    val languageC: String,
    val timeD: String,
)
