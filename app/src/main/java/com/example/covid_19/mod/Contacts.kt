package com.example.covid_19.mod


import com.google.gson.annotations.SerializedName

data class Contacts(
    @SerializedName("primary")
    val primary: Primary,
    @SerializedName("regional")
    val regional: List<Regional>
)