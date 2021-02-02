package com.example.covid_19.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.covid_19.R
import com.example.covid_19.adapters.AdapterRvNews
import com.example.covid_19.api.ApiInterface
import com.example.covid_19.newsmod.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsFragment : Fragment(), AdapterRvNews.ItemClicked {
    private var rvNews: RecyclerView? = null
    private var tvNoDataReceived: TextView? = null
    private var adapterRvNews: AdapterRvNews? = null
    private var receivedNewsData: ArrayList<NewsData> = ArrayList()
    private var mContext: Context? = null
    private var loadingAnimation: LottieAnimationView? = null
    private var v: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_news, container, false)


        init()

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun init() {
        rvNews = v!!.findViewById(R.id.rvNews)
        tvNoDataReceived = v!!.findViewById(R.id.tvNoDataReceived)
        loadingAnimation = v!!.findViewById(R.id.loadingAnimationNews)
    }

    private fun setRecyclerView() {
        if (receivedNewsData.size != 0) {
            rvNews!!.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapterRvNews = AdapterRvNews(mContext!!, receivedNewsData, this)
            rvNews!!.adapter = adapterRvNews
            loadingAnimation!!.cancelAnimation()
            loadingAnimation!!.visibility = View.GONE
        } else {
            tvNoDataReceived!!.visibility = View.VISIBLE
            loadingAnimation!!.cancelAnimation()
            loadingAnimation!!.visibility = View.GONE
        }
    }


    fun setData(list: ArrayList<NewsData>) {
        receivedNewsData = list
        setRecyclerView()
    }

    override fun onItemClicked(position: Int) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(receivedNewsData[0].news[position].link))
        startActivity(browserIntent)
    }
}