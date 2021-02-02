package com.example.covid_19.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.covid_19.R
import com.example.covid_19.adapters.ViewPagerAdapter
import com.example.covid_19.api.ApiInterface
import com.example.covid_19.fragments.HelpFragment
import com.example.covid_19.fragments.HomeFragment
import com.example.covid_19.fragments.NewsFragment
import com.example.covid_19.modal.CountryData
import com.example.covid_19.newsmod.NewsData
import com.ogaclejapan.smarttablayout.SmartTabLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private var receivedNewsData: ArrayList<NewsData> = ArrayList()
    private var receivedCountryData: ArrayList<CountryData> = ArrayList()
    private val newsFragment = NewsFragment()
    private val homeFragment = HomeFragment()
    private val helpFragment = HelpFragment()
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private val mFragmentTitleList: ArrayList<String> = ArrayList()
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var districtCases = 0
    private var districtName: String? = null
    private var stateName: String? = null
    private var helplineNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        districtName = intent.getStringExtra("DistrictName")
        stateName = intent.getStringExtra("StateName")
        districtCases = intent.getIntExtra("DistrictCases", 0)
        helplineNumber = intent.getStringExtra("HelplineNumber")
        init()

        homeFragment.showParticulars(districtName!!, stateName!!, districtCases, helplineNumber!!)
        fetchNewsData()
    }

    private fun fetchNewsData() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://cryptic-ravine-96718.herokuapp.com")
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val myCall = apiInterface.getNewsData()
        myCall.enqueue(object : Callback<NewsData> {
            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }
            override fun onResponse(
                call: Call<NewsData>,
                response: Response<NewsData>
            ) {
                Log.e("onSuccess", response.toString())
                var data = response.body()!!
                receivedNewsData.add(data)
                newsFragment.setData(receivedNewsData)
            }
        })
    }

    private fun init() {
        mFragmentList.add(homeFragment)
        mFragmentList.add(newsFragment)
        mFragmentList.add(helpFragment)
        mFragmentTitleList.add("Home")
        mFragmentTitleList.add("News")
        mFragmentTitleList.add("Help")
        viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, mFragmentList, mFragmentTitleList)
        viewpagerMain!!.adapter = viewPagerAdapter
        tabLayoutMain!!.setupWithViewPager(viewpagerMain)
    }
}