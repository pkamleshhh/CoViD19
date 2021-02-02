package com.example.covid_19.activities

import android.Manifest
import android.R.string
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.covid_19.R
import com.example.covid_19.api.ApiInterface
import com.example.covid_19.mod.HelplineNumbers
import com.example.covid_19.modal.DistrictDataList
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class SplashActivity : AppCompatActivity() {
    private var receivedDistrictData: ArrayList<DistrictDataList> = ArrayList()
    private var receivedHelplineNumbers: ArrayList<HelplineNumbers> = ArrayList()
    private var address: ArrayList<Address> = ArrayList()
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var districtCases = 0
    private var helplineNumber: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestPermission()
        fetchHelpLineNumbers()
        fetchApiDataCountry()

    }

    private fun setParticulars() {
        for (obj in receivedDistrictData[0]) {
            if (obj.state == address[0].adminArea) {
                for (p in obj.districtData) {
                    if (p.district == address[0].subAdminArea) {
                        districtCases = p.active
                    }
                }
            }
        }
        for (obj in receivedHelplineNumbers) {
            for (p in obj.data.contacts.regional) {
                if (p.loc == address[0].adminArea) {
                    helplineNumber = p.number
                }
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("DistrictName", address[0].subAdminArea)
        intent.putExtra("StateName", address[0].adminArea)
        intent.putExtra("DistrictCases", districtCases)
        intent.putExtra("HelplineNumber", helplineNumber)
        startActivity(intent)
        finish()
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                44
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient!!.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location != null) {
                val geoCoder = Geocoder(this, Locale.getDefault())
                val list = (geoCoder.getFromLocation(location.latitude, location.longitude, 1))
                address.addAll(list)
            }
        }
    }

    private fun fetchApiDataCountry() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.covid19india.org/")
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val callForDistrict = apiInterface.getDistrictData()
        callForDistrict.enqueue(object : Callback<DistrictDataList> {
            override fun onResponse(
                call: Call<DistrictDataList>,
                response: Response<DistrictDataList>
            ) {
                Log.e("onSuccess", response.toString())
                var data = response.body()!!
                receivedDistrictData.add(data)
                setParticulars()
            }

            override fun onFailure(call: Call<DistrictDataList>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }
        })
    }

    private fun fetchHelpLineNumbers() {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.rootnet.in/")
            .build()

        val apiInterface = retrofit.create(ApiInterface::class.java)
        val callForNumbers = apiInterface.getHelplineNumbers()
        callForNumbers.enqueue(object : Callback<HelplineNumbers> {
            override fun onResponse(
                call: Call<HelplineNumbers>,
                response: Response<HelplineNumbers>
            ) {
                Log.e("onSuccess", response.toString())
                var data = response.body()!!
                receivedHelplineNumbers.add(data)
            }

            override fun onFailure(call: Call<HelplineNumbers>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
            }

        })
    }


}