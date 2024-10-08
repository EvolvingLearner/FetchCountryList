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
sealed interface ListItem {
    data class CountryListItem(val country: Country) : ListItem
    data class HeaderListItem(val header: Char) : ListItem
}

class CountryViewModel(private val countryRepository: CountriesRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val countryList = MutableLiveData<List<ListItem>>()
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
                            countryList.value = prepareHeaderList(result.data)
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

    private fun prepareHeaderList(list: List<Country>): List<ListItem> {
        val listItem = mutableListOf<ListItem>()
        var previousHeader: Char? = null
        list.sortedBy { it.name }.forEach { country ->
            val current = country.name.first() // A
            if (previousHeader == null || previousHeader != current) {
                listItem.add(ListItem.HeaderListItem(current))
                previousHeader = current
            }
            listItem.add(ListItem.CountryListItem(country))
        }
        return listItem
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
