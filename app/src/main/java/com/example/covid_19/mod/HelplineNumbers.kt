package com.example.covid_19.mod


import com.google.gson.annotations.SerializedName

data class HelplineNumbers(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("lastOriginUpdate")
    val lastOriginUpdate: String,
    @SerializedName("lastRefreshed")
    val lastRefreshed: String,
    @SerializedName("success")
    val success: Boolean
)