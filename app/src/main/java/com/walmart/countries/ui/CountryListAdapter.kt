package com.walmart.countries.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walmart.countries.R

/**
 * CountryListAdapter that display a ItemViewHolder items form API.
 */
class CountryListAdapter : ListAdapter<ListItem, ItemViewHolder>(CountryComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_list_item, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

}

object CountryComparator : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}


class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(listItem: ListItem) {
        val countryTV = view.findViewById<TextView>(R.id.country_name)
        val regionTV = view.findViewById<TextView>(R.id.country_region)
        val codeTV = view.findViewById<TextView>(R.id.country_code)
        val capitalTV = view.findViewById<TextView>(R.id.country_capital)
        if (listItem is ListItem.CountryListItem) {
            countryTV.text = listItem.country.name + view.context.getString(R.string.comma)
            regionTV.text = listItem.country.region
            codeTV.text = listItem.country.code
            capitalTV.text = listItem.country.capital
        } else if (listItem is ListItem.HeaderListItem) {
            countryTV.text = listItem.header.toString()
            regionTV.text = ""
            codeTV.text = ""
            capitalTV.text = ""
        }

    }
}