package com.example.covid_19.modal


import com.google.gson.annotations.SerializedName

data class DistrictDataItem(
    @SerializedName("districtData")
    val districtData: List<DistrictData>,
    @SerializedName("state")
    val state: String,
    @SerializedName("statecode")
    val statecode: String
)