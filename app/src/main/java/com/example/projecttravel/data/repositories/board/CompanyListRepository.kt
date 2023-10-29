package com.example.projecttravel.data.repositories.board

import com.example.projecttravel.model.board.Company
import com.example.projecttravel.network.TravelApiService

/**
 * Repository retrieves Board data from underlying data source.
 */
interface CompanyListRepository {
    /** Retrieves list of Board from underlying data source */
    suspend fun getCompanyList(): List<Company>
}

/**
 * Network Implementation of repository that retrieves Country data from underlying data source.
 */
class DefaultCompanyListRepository(
    private val travelApiService: TravelApiService
) : CompanyListRepository {
    /** Retrieves list of Board from underlying data source */
    override suspend fun getCompanyList(): List<Company> = travelApiService.getCompanyList()
}
