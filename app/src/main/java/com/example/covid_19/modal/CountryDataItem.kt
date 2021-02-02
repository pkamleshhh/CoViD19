package com.example.covid_19.modal


import com.google.gson.annotations.SerializedName

data class CountryDataItem(
    @SerializedName("active")
    val active: Int,
    @SerializedName("activePerOneMillion")
    val activePerOneMillion: Double,
    @SerializedName("cases")
    val cases: Int,
    @SerializedName("casesPerOneMillion")
    val casesPerOneMillion: Int,
    @SerializedName("continent")
    val continent: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("countryInfo")
    val countryInfo: CountryInfo,
    @SerializedName("critical")
    val critical: Int,
    @SerializedName("criticalPerOneMillion")
    val criticalPerOneMillion: Double,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("deathsPerOneMillion")
    val deathsPerOneMillion: Double,
    @SerializedName("oneCasePerPeople")
    val oneCasePerPeople: Int,
    @SerializedName("oneDeathPerPeople")
    val oneDeathPerPeople: Int,
    @SerializedName("oneTestPerPeople")
    val oneTestPerPeople: Int,
    @SerializedName("population")
    val population: Int,
    @SerializedName("recovered")
    val recovered: Int,
    @SerializedName("recoveredPerOneMillion")
    val recoveredPerOneMillion: Double,
    @SerializedName("tests")
    val tests: Int,
    @SerializedName("testsPerOneMillion")
    val testsPerOneMillion: Int,
    @SerializedName("todayCases")
    val todayCases: Int,
    @SerializedName("todayDeaths")
    val todayDeaths: Int,
    @SerializedName("todayRecovered")
    val todayRecovered: Int,
    @SerializedName("updated")
    val updated: Long
)