package com.example.covid_19.fragments

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19.R
import com.example.covid_19.adapters.AdapterRvStateList
import com.example.covid_19.modal.DistrictDataList
import com.example.covid_19.modal.StateDataList
import com.example.covid_19.modal.Statewise

class CountryPageFragment : Fragment(), AdapterRvStateList.ItemClicked {
    private var btnSearch: Button? = null
    private var etDistrict: EditText? = null
    private var receivedStateData: ArrayList<StateDataList> = ArrayList()
    private var receivedDistrictData: ArrayList<DistrictDataList> = ArrayList()
    private var adapterRvStateList: AdapterRvStateList? = null
    private var rvStateList: RecyclerView? = null
    private var mContext: Context? = null
    private var v: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.country_page_fragment, container, false)
        init()

        btnSearch!!.setOnClickListener {
            val etDistrictName: String = etDistrict!!.text.toString()
            etDistrictName.substring(0, 1).toUpperCase() + etDistrictName.substring(1).toLowerCase()
            for (p in receivedStateData) {
                for (q in p.statewise) {
                    if (q.state == etDistrictName) {
                        openDialog(q)
                    }
                }
            }
        }

        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    private fun init() {
        btnSearch = v!!.findViewById(R.id.btnSearch)
        rvStateList = v!!.findViewById(R.id.rvStateList)
        etDistrict = v!!.findViewById(R.id.etDistrict)

    }

    private fun setRecyclerView() {
        if (receivedDistrictData.size != 0 && receivedStateData.size != 0) {
            rvStateList!!.layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            adapterRvStateList = AdapterRvStateList(mContext!!, receivedStateData, this)
            rvStateList!!.adapter = adapterRvStateList
        } else {

        }
    }

    fun fetchStateAndDistrictData(
        stateData: ArrayList<StateDataList> = ArrayList(),
        districtData: ArrayList<DistrictDataList> = ArrayList()
    ) {
        receivedStateData = stateData
        receivedDistrictData = districtData
        setRecyclerView()
    }


    private fun startCountAnimation(upperLimit: Int, textView: TextView) {
        val animator = ValueAnimator.ofInt(0, upperLimit)
        animator.duration = 800
        animator.addUpdateListener { animation ->
            textView.text = animation.animatedValue.toString()
        }
        animator.start()
    }

    override fun onItemClicked(position: Int) {
        val mObject = receivedStateData[0].statewise[position]
        openDialog(mObject)
    }

    private fun openDialog(obj: Statewise) {
        val dialog = Dialog(mContext!!)
        dialog.setContentView(R.layout.temp)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.white)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.anim.zoom_out
        val ivFlag: ImageView = dialog.findViewById(R.id.ivFlagDialog)
        ivFlag.visibility = View.GONE
        val tvStateName: TextView = dialog.findViewById(R.id.tvCountryName)
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
        val cvCritical: CardView = dialog.findViewById(R.id.cvCritical)
        cvCritical.visibility = View.GONE
        tvCasesPerMillion.visibility = View.GONE
        tvCaseToday.visibility = View.GONE
        tvDataActiveNew.visibility = View.GONE
        tvDataActivePerMillion.visibility = View.GONE
        tvDataRecoveredNew.visibility = View.GONE
        tvDataRecoveredPerMillion.visibility = View.GONE
        tvDataDeathsNew.visibility = View.GONE
        tvDataDeathPerMillion.visibility = View.GONE
        tvStateName.text = obj.state
        startCountAnimation(obj.confirmed.toInt(), tvDataConfirmedCase)
        startCountAnimation(obj.active.toInt(), tvDataActiveCases)
        startCountAnimation(obj.recovered.toInt(), tvDataRecovered)
        startCountAnimation(obj.deaths.toInt(), tvDataDeaths)
        dialog.setCancelable(true)
        dialog.show()
    }


}