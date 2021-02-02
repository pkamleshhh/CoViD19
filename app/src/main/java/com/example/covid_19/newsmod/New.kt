package com.example.covid_19.newsmod


import com.google.gson.annotations.SerializedName

data class New(
    @SerializedName("img")
    val img: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("title")
    val title: String
)