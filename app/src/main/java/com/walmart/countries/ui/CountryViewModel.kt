package com.walmart.countries.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.countries.data.Country
import com.walmart.countries.model.NetworkState
import com.walmart.countries.repository.CountriesRepository
import com.walmart.countries.util.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * CountryViewModel which extends android ViewModel() which stores VM data in memory after UI recreation.
 * Fetch Countries list from repository using Coroutine and store in MutableLiveData
 * Handle NetworkState on response Conditions
 */
class CountryViewModel(private val countryRepository: CountriesRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val countryList = MutableLiveData<List<Country>>()
    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllCountries() {

        job = viewModelScope.launch {
            countryRepository.getAllCountries().flowOn(Dispatchers.IO).catch { exceptionHandler }
                .collect { result ->
                    when (result) {
                        is NetworkState.Success -> {
                            countryList.postValue(result.data)
                        }

                        is NetworkState.Error -> {
                            if (result.response.code() == 401) {
                                onError(Constants.ERROR + "${result.response.code()}")
                            } else {
                                onError(Constants.ERROR + "${result.response.message()} ")
                            }
                        }
                    }
                }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
