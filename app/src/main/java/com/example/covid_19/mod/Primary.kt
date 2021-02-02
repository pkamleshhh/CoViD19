package com.example.covid_19.mod


import com.google.gson.annotations.SerializedName

data class Primary(
    @SerializedName("email")
    val email: String,
    @SerializedName("facebook")
    val facebook: String,
    @SerializedName("media")
    val media: List<String>,
    @SerializedName("number")
    val number: String,
    @SerializedName("number-tollfree")
    val numberTollfree: String,
    @SerializedName("twitter")
    val twitter: String
)