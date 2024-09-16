package com.walmart.countries

import android.app.Application
import com.walmart.countries.di.AppContainer
import com.walmart.countries.di.AppModule

class CountryApplication : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppContainer(this)
    }
}