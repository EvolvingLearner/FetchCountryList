package com.walmart.countries.di

import com.walmart.countries.model.ApiService
import com.walmart.countries.repository.CountriesRepository

interface AppModule {
    val apiService: ApiService
    val countriesRepository: CountriesRepository
}