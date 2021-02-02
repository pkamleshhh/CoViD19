package com.example.covid_19.fragments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.covid_19.R
import com.example.covid_19.adapters.AdapterRvCountryList
import com.example.covid_19.modal.CountryData
import com.example.covid_19.modal.CountryDataItem

class GlobalPageFragment : Fragment(), AdapterRvCountryList.ItemClicked {
    private var v: View? = null
    private var adapterRvCountryList: AdapterRvCountryList? = null
    private var rvGlobalFrag: RecyclerView? = null
    private var receivedDataList: ArrayList<CountryData> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mContext: Context? = null
    private var fadeInAnimation: Animation? = null
    private var slideUp: Animation? = null
    private var layoutContainer: LinearLayout? = null
    private var tvNoDataReceived: TextView? = null
    private var loadingAnimation: LottieAnimationView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.global_page_fragment, container, false)
        init()
        return v
    }

    private fun init() {
        fadeInAnimation =
            AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.fade_in)
        slideUp = AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.slide_up)
        rvGlobalFrag = v!!.findViewById(R.id.rvCountryInfo)
        tvNoDataReceived = v!!.findViewById(R.id.tvNoDataReceivedGlobalFrag)
        loadingAnimation = v!!.findViewById(R.id.loadingAnimation)
    }

    private fun setRecyclerView() {
        if (receivedDataList.size != 0) {
            rvGlobalFrag!!.layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapterRvCountryList = AdapterRvCountryList(mContext!!, receivedDataList, this)
            rvGlobalFrag!!.adapter = adapterRvCountryList
            loadingAnimation!!.cancelAnimation()
            loadingAnimation!!.visibility = View.GONE
        } else {
            tvNoDataReceived!!.visibility = View.VISIBLE
            loadingAnimation!!.cancelAnimation()
            loadingAnimation!!.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun startCountAnimation(upperLimit: Int, textView: TextView) {
        val animator = ValueAnimator.ofInt(0, upperLimit)
        animator.duration = 800
        animator.addUpdateListener { animation ->
            textView.text = animation.animatedValue.toString()
        }
        animator.start()
    }

    fun fetchData(countryData: ArrayList<CountryData> = ArrayList()) {
        receivedDataList.addAll(countryData)
        setRecyclerView()
    }

    override fun onItemClicked(position: Int) {
        val mObject = receivedDataList[0][position]
        openDialog(mObject)
    }

    @SuppressLint("SetTextI18n")
    private fun openDialog(obj: CountryDataItem) {
        val dialog = Dialog(mContext!!)
        dialog.setContentView(R.layout.temp)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.white)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.anim.zoom_out
        val ivFlag: ImageView = dialog.findViewById(R.id.ivFlagDialog)
        val tvCountryName: TextView = dialog.findViewById(R.id.tvCountryName)
        val tvDataConfirmedCase: TextView = dialog.findViewById(R.id.tvDataConfirmedCase)
        val tvCasesPerMillion: TextView = dialog.findViewById(R.id.tvConfirmedCasePerMillion)
        val tvCaseToday: TextView = dialog.findViewById(R.id.tvDataConfirmedNew)
        val tvDataActiveCases: TextView = dialog.findViewById(R.id.tvDataActiveCases)
        val tvDataActiveNew: TextView = dialog.findViewById(R.id.tvDataActiveNew)
        val tvDataActivePerMillion: TextView = dialog.findViewById(R.id.tvDataActivePerMillion)
        val tvDataRecovered: TextView = dialog.findViewById(R.id.tvDataRecovered)
        val tvDataRecoveredNew: TextView = dialog.findViewById(R.id.tvDataRecoveredNew)
        val tvDataRecoveredPerMillion: TextView =
            dialog.findViewById(R.id.tvDataRecoveredPerMillion)
        val tvDataDeaths: TextView = dialog.findViewById(R.id.tvDataDeaths)
        val tvDataDeathsNew: TextView = dialog.findViewById(R.id.tvDataDeathsNew)
        val tvDataDeathPerMillion: TextView = dialog.findViewById(R.id.tvDataDeathPerMillion)
        val tvDataCritical: TextView = dialog.findViewById(R.id.tvDataCritical)
        val tvDataCriticalNew: TextView = dialog.findViewById(R.id.tvDataCriticalNew)
        val tvDataCriticalPerMillion: TextView = dialog.findViewById(R.id.tvDataCriticalPerMillion)
        tvCountryName.text = obj.country + " (" + obj.continent + ") " + obj.population.toString()
        Glide.with(mContext!!).load(obj.countryInfo.flag)
            .apply(RequestOptions().override(200, 120)).into(ivFlag)
        startCountAnimation(obj.cases, tvDataConfirmedCase)
        tvCasesPerMillion.text = obj.casesPerOneMillion.toString() + " (per million)"
        tvCaseToday.text = "+" + obj.todayCases + " today"
        tvCaseToday.startAnimation(slideUp)
        startCountAnimation(obj.active, tvDataActiveCases)
        tvDataActivePerMillion.text = obj.activePerOneMillion.toString() + " (per million)"
        startCountAnimation(obj.recovered, tvDataRecovered)
        tvDataRecoveredNew.text = "+" + obj.todayRecovered + " today"
        tvDataRecoveredNew.startAnimation(slideUp)
        tvDataRecoveredPerMillion.text = obj.recoveredPerOneMillion.toString() + " (per million)"
        startCountAnimation(obj.critical, tvDataCritical)
        tvDataCriticalPerMillion.text = obj.criticalPerOneMillion.toString() + " (per million)"
        startCountAnimation(obj.deaths, tvDataDeaths)
        tvDataDeathsNew.text = "+" + obj.todayDeaths + " today"
        tvDataDeathsNew.startAnimation(slideUp)
        tvDataDeathPerMillion.text = obj.deathsPerOneMillion.toString() + " (per million)"
        dialog.setCancelable(true)
        dialog.show()
    }

}