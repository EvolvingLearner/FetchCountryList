package com.walmart.countries.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.walmart.countries.R
import com.walmart.countries.data.Country
import com.walmart.countries.databinding.CountryListItemBinding

/**
 * CountryListRecyclerViewAdapter that display a ItemViewHolder items form API.
 */
class CountryListRecyclerViewAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var countryList = mutableListOf<Country>()

    fun setCountries(countries: List<Country>) {
        countryList.clear()
        this.countryList = countries.toMutableList()
        this.notifyDataSetChanged();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val country = countryList[position]
        holder.binding.countryName.text =
            country.name + holder.itemView.context.getString(R.string.comma)
        holder.binding.countryRegion.text = country.region
        holder.binding.countryCode.text = country.code
        holder.binding.countryCapital.text = country.capital
        holder.itemView.setOnClickListener {
            // Handle item click
        }
    }

    override fun getItemCount(): Int = countryList.size
}

class ItemViewHolder(val binding: CountryListItemBinding) : RecyclerView.ViewHolder(binding.root)