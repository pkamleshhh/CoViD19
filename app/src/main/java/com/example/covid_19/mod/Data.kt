package com.example.covid_19.mod


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("contacts")
    val contacts: Contacts
)