package com.example.covid_19.newsmod


import com.google.gson.annotations.SerializedName

data class NewsData(
    @SerializedName("news")
    val news: List<New>
)