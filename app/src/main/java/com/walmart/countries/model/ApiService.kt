package com.walmart.countries.model

import com.walmart.countries.data.Country
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("countries.json")
    suspend fun getAllCountriesList(): Response<List<Country>>
}