package com.example.covid_19.modal


import com.google.gson.annotations.SerializedName

data class Delta(
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("deceased")
    val deceased: Int,
    @SerializedName("recovered")
    val recovered: Int
)