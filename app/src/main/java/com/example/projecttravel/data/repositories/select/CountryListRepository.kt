package com.example.projecttravel.data.repositories.select

import com.example.projecttravel.model.select.CountryInfo
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Country data from underlying data source.
 */
interface CountryListRepository {
    /** Retrieves list of amphibians from underlying data source */
    suspend fun getCountryList(): List<CountryInfo>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultCountryListRepository(
    private val travelApiService: TravelApiService
) : CountryListRepository {
    /** Retrieves list of Country from underlying data source */
    override suspend fun getCountryList(): List<CountryInfo> = travelApiService.getCountryList()
}