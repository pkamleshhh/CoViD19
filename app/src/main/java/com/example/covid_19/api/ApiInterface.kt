package com.example.covid_19.api


import com.example.covid_19.mod.HelplineNumbers
import com.example.covid_19.modal.CountryData
import com.example.covid_19.modal.DistrictDataList
import com.example.covid_19.modal.StateDataList
import com.example.covid_19.newsmod.NewsData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("v3/covid-19/countries")
    fun getCountryData(): Call<CountryData>

    @GET("data.json")
    fun getStateData(): Call<StateDataList>

    @GET("v2/state_district_wise.json")
    fun getDistrictData(): Call<DistrictDataList>

    @GET("/")
    fun getNewsData(): Call<NewsData>

    @GET("covid19-in/contacts")
    fun getHelplineNumbers(): Call<HelplineNumbers>
}