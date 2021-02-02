package com.example.covid_19.modal


import com.google.gson.annotations.SerializedName

data class DistrictData(
    @SerializedName("active")
    val active: Int,
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("deceased")
    val deceased: Int,
    @SerializedName("delta")
    val delta: Delta,
    @SerializedName("district")
    val district: String,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("recovered")
    val recovered: Int
)