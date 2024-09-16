package com.walmart.countries.repository

import com.walmart.countries.data.Country
import com.walmart.countries.model.ApiService
import com.walmart.countries.model.NetworkState
import com.walmart.countries.model.parseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountriesRepository(private val retrofitService: ApiService) {

    suspend fun getAllCountries(): Flow<NetworkState<List<Country>>> {
        val response = retrofitService.getAllCountriesList().parseResponse()
        return flow {
            emit(response)
        }
    }
}
