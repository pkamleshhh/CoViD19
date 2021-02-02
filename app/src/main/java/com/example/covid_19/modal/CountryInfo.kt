package com.example.covid_19.modal


import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("flag")
    val flag: String,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("iso2")
    val iso2: String,
    @SerializedName("iso3")
    val iso3: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("long")
    val long: Double
)