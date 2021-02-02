package com.example.covid_19.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.covid_19.R
import com.example.covid_19.activities.StatisticsActivity
import com.example.covid_19.mod.HelplineNumbers


class HomeFragment : Fragment() {


    private var v: View? = null
    private var btnCheckStats: Button? = null
    private var btnCall: Button? = null
    private var tvParticulars: TextView? = null
    private var districtCases = 0
    private var districtName: String? = null
    private var stateName: String? = null
    private var helplineNumbers: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)

        init()

        btnCheckStats!!.setOnClickListener {
            val intent = Intent(context, StatisticsActivity::class.java)
            startActivity(intent)
        }
        btnCall!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$helplineNumbers")
            startActivity(intent)
        }
        return v
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        btnCheckStats = v!!.findViewById(R.id.btnCheckStats)
        tvParticulars = v!!.findViewById(R.id.tvParticulars)
        btnCall = v!!.findViewById(R.id.btnCall)
        tvParticulars!!.text = "$districtCases active cases in $districtName,$stateName"
    }

    fun showParticulars(district: String, state: String, cases: Int, number: String) {
        districtName = district
        stateName = state
        districtCases = cases
        helplineNumbers = number
    }

}