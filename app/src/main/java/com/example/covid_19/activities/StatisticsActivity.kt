package com.example.covid_19.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.covid_19.R
import com.example.covid_19.adapters.ViewPagerAdapter
import com.example.covid_19.api.ApiInterface
import com.example.covid_19.fragments.CountryPageFragment
import com.example.covid_19.fragments.GlobalPageFragment
import com.example.covid_19.modal.CountryData
import com.example.covid_19.modal.DistrictDataList
import com.example.covid_19.modal.StateDataList
import kotlinx.android.synthetic.main.layout_stats.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StatisticsActivity : AppCompatActivity() {
    private var globalPageFragment = GlobalPageFragment()
    private var countryPageFragment = CountryPageFragment()
    private var receivedCountryData: ArrayList<CountryData> = ArrayList()
    private var receivedStateData: ArrayList<StateDataList> = ArrayList()
    private var receivedDistrictData: ArrayList<DistrictDataList> = ArrayList()
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList: ArrayList<String> = ArrayList()
    private var viewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_stats)
        init()
        fetchCountryData()
        fetchApiDataCountry()
        ivBack.setOnClickListener {
            finish()
        }

    }

    private fun fetchCountryData() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://corona.lmao.ninja/")
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val myCall = apiInterface.getCountryData()
        myCall.enqueue(object : Callback<CountryData> {
            override fun onFailure(call: Call<CountryData>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

            override fun onResponse(
                call: Call<CountryData>,
                response: Response<CountryData>
            ) {
                Log.e("onSuccess", response.toString())
                var data = response.body()!!
                receivedCountryData.add(data)
                globalPageFragment.fetchData(receivedCountryData)
            }

        })

    }

    private fun fetchApiDataCountry() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.covid19india.org/")
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val myCall = apiInterface.getStateData()
        myCall.enqueue(object : Callback<StateDataList> {
            override fun onFailure(call: Call<StateDataList>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

            override fun onResponse(
                call: Call<StateDataList>,
                response: Response<StateDataList>
            ) {
                Log.e("onSuccess", response.toString())
                var data = response.body()!!
                receivedStateData.add(data)
            }
        })
        val myCall2 = apiInterface.getDistrictData()
        myCall2.enqueue(object : Callback<DistrictDataList> {
            override fun onResponse(
                call: Call<DistrictDataList>,
                response: Response<DistrictDataList>
            ) {
                var data = response.body()!!
                receivedDistrictData.add(data)
                rlContainer.visibility = View.VISIBLE
                countryPageFragment.fetchStateAndDistrictData(
                    receivedStateData,
                    receivedDistrictData
                )
            }

            override fun onFailure(call: Call<DistrictDataList>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

        })
    }

    private fun init() {
        mFragmentList.add(globalPageFragment)
        mFragmentList.add(countryPageFragment)
        mFragmentTitleList.add("Global")
        mFragmentTitleList.add("Country")
        viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, mFragmentList, mFragmentTitleList)
        viewpager!!.adapter = viewPagerAdapter
        tabLayout!!.setupWithViewPager(viewpager)
    }
}