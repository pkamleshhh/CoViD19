package com.example.covid_19.mod


import com.google.gson.annotations.SerializedName

data class Regional(
    @SerializedName("loc")
    val loc: String,
    @SerializedName("number")
    val number: String
)