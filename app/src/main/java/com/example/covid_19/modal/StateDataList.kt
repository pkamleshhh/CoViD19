package com.example.covid_19.modal


import com.google.gson.annotations.SerializedName

data class StateDataList(
    @SerializedName("cases_time_series")
    val casesTimeSeries: List<CasesTimeSery>,
    @SerializedName("statewise")
    val statewise: List<Statewise>,
    @SerializedName("tested")
    val tested: List<Tested>
)