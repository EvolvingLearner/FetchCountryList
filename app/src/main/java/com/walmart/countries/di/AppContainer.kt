package com.walmart.countries.di

import android.content.Context
import com.walmart.countries.model.ApiService
import com.walmart.countries.repository.CountriesRepository
import com.walmart.countries.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AppContainer(private val appContext: Context) : AppModule {

    override val apiService: ApiService
            by lazy {
                Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create()
            }
    override val countriesRepository: CountriesRepository
            by lazy { CountriesRepository(apiService) }

}