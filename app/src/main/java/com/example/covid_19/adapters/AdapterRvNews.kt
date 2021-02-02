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
import com.example.covid_19.modal.StateDataList
import com.example.covid_19.newsmod.NewsData


class AdapterRvNews(
    private val context: Context,
    private val newsData: ArrayList<NewsData> = ArrayList(),
    private val itemClicked: ItemClicked
) :
    RecyclerView.Adapter<AdapterRvNews.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view_news, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return newsData[0].news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text=newsData[0].news[position].title
        Glide.with(context).load(newsData[0].news[position].img)
            .apply(RequestOptions().override(200,180)).into(holder.ivThumbnail)
        holder.parent.setOnClickListener {
            itemClicked.onItemClicked(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvNewsTitle)!!
        val ivThumbnail = itemView.findViewById<ImageView>(R.id.ivThumbnail)!!
        val parent = itemView.findViewById<ConstraintLayout>(R.id.parentNews)!!
    }

    interface ItemClicked {
        fun onItemClicked(position: Int)
    }
}