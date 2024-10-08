package com.walmart.countries.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.walmart.countries.CountryApplication
import com.walmart.countries.databinding.FragmentCountryListBinding
import com.walmart.countries.model.ViewModelFactory


/**
 * CountryListFragment representing a countries list of Items.
 * Fetch list from viewmodel and set to CountryListAdapter
 * Show Error message on Error Conditions
 */
class CountryListFragment : Fragment() {

    private val viewModel: CountryViewModel by viewModels { ViewModelFactory(CountryApplication.appModule.countriesRepository) }
    private lateinit var _adapter: CountryListAdapter
    private lateinit var countryListBinding: FragmentCountryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        countryListBinding = FragmentCountryListBinding.inflate(inflater, container, false)
        countryListBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        _adapter = CountryListAdapter()
        // fetch countries list
        viewModel.getAllCountries()
        return countryListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryList.observe(viewLifecycleOwner) { countries ->
            countries?.let {
                _adapter.submitList(it)
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        countryListBinding.recyclerView.adapter = _adapter
    }
}