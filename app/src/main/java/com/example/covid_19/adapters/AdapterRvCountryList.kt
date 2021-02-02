package com.example.covid_19.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.covid_19.R
import com.example.covid_19.modal.CountryData


class AdapterRvCountryList(private val context: Context, private val countryData:ArrayList<CountryData> =ArrayList(), private val itemClicked: ItemClicked) :
    RecyclerView.Adapter<AdapterRvCountryList.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view_country_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return countryData[0].size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvCountryItemView.text=countryData[0][position].country
        holder.tvConfirmedItemViewCountry.text=countryData[0][position].active.toString()
        Glide.with(context).load(countryData[0][position].countryInfo.flag).apply(RequestOptions().override(90,75)).into(holder.ivCountryFlag)
        holder.parent.setOnClickListener {
            itemClicked.onItemClicked(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCountryItemView = itemView.findViewById<TextView>(R.id.tvCountryNameItemView)!!
        val tvConfirmedItemViewCountry = itemView.findViewById<TextView>(R.id.tvConfirmedItemViewCountry)!!
        val ivCountryFlag= itemView.findViewById<ImageView>(R.id.ivCountryFlag)!!
        val parent = itemView.findViewById<ConstraintLayout>(R.id.parent)!!

    }

    interface ItemClicked {
        fun onItemClicked(position: Int)
    }
}