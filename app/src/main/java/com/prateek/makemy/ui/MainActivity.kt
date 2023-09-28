package com.prateek.makemy.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.prateek.makemy.R
import com.prateek.makemy.base.BaseActivity
import com.prateek.makemy.base.MmtApp
import com.prateek.makemy.data.DataProvider
import com.prateek.makemy.databinding.ActivityMainBinding
import com.prateek.makemy.interfaces.ServiceItemClickListener
import com.prateek.makemy.interfaces.TrendingItemClickListener
import com.prateek.makemy.models.ServiceItem
import com.prateek.makemy.models.TrendingItem
import com.prateek.makemy.util.SearchUtil

class MainActivity : BaseActivity(), ServiceItemClickListener, TrendingItemClickListener {


    private lateinit var binding : ActivityMainBinding
    private lateinit var serviceAdapter: ServiceAdapter
    private lateinit var trendingAdapter: TrendingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //if not logged in log in user
        val isLoggedIn = (application as MmtApp).userSignedIn
        if(!isLoggedIn){
            Toast.makeText(this, "Please Log In", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else{
            binding.tvUser.text = (application as MmtApp).username
        }

        binding.bottom.setOnItemSelectedListener {
            Toast.makeText(this, "Bottom Navigation Yet not Implemented", Toast.LENGTH_SHORT).show()
        }


        Log.d("Progress", "Initialization of Dummy Data")
        // dummy data
        val serviceList = listOf(
            ServiceItem("Flight", ContextCompat.getDrawable(this, R.drawable.baseline_flight_24)!!),
            ServiceItem("Trains", ContextCompat.getDrawable(this, R.drawable.baseline_train_24)!!),
            ServiceItem("Buses", ContextCompat.getDrawable(this, R.drawable.baseline_directions_bus_24)!!),
            ServiceItem("Cruises", ContextCompat.getDrawable(this, R.drawable.baseline_directions_bus_24)!!),
            ServiceItem("Holidays", ContextCompat.getDrawable(this, R.drawable.baseline_imagesearch_roller_24)!!),
            ServiceItem("Electric Bike", ContextCompat.getDrawable(this, R.drawable.baseline_electric_bike_24)!!),
            ServiceItem("Hotels", ContextCompat.getDrawable(this, R.drawable.baseline_airline_seat_flat_24)!!)
        )


        val trendingList = listOf(
            TrendingItem("30% off on Flights", ContextCompat.getDrawable(this, R.drawable.flight1)!!),
            TrendingItem("10% off on International Flights", ContextCompat.getDrawable(this, R.drawable.flight)!!),
            TrendingItem("50% off on Packages", ContextCompat.getDrawable(this, R.drawable.holiday1)!!),
            TrendingItem("The Amazing Mountains", ContextCompat.getDrawable(this, R.drawable.mountains1)!!),
            TrendingItem("Win a free ticket", ContextCompat.getDrawable(this, R.drawable.holiday)!!),
            TrendingItem("Free Trip to Goa", ContextCompat.getDrawable(this, R.drawable.beach)!!)
        )

        Log.d("Progress", "Initialization of Dummy Data DONE")

        serviceAdapter = ServiceAdapter(serviceList, this, this)
        trendingAdapter = TrendingAdapter(trendingList, this, this)

        Log.d("Progress", "Initialization of Adapter done")

        binding.rvServices.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvServices.setHasFixedSize(true)

        binding.rvServices.adapter = serviceAdapter

        binding.rvTrending.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.rvTrending.setHasFixedSize(true)

        binding.rvTrending.adapter = trendingAdapter

        // add listener in search box
        binding.etSearch.doOnTextChanged { text, start, before, count ->
            if(count == 0){
                serviceAdapter.updateData(serviceList)
            }else{
                val updatedList = SearchUtil.searchServices(text.toString(), serviceList)
                if(!updatedList.isNullOrEmpty()) {
                    serviceAdapter.updateData(updatedList)
                }
                else
                    showNoServicesFoundUi()
            }
        }


        binding.tvLogOut.setOnClickListener{
            performLogout()
        }





    }

    private fun performLogout() {
        (application as MmtApp).userSignedIn = false
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showNoServicesFoundUi() {
        Toast.makeText(this, "No Service Found", Toast.LENGTH_SHORT).show()
    }

    override fun onServiceItemClicked(serviceName: String) {
        Toast.makeText(this, "$serviceName is available", Toast.LENGTH_SHORT).show()
    }

    override fun onTrendingItemClicked(trendingName: String) {
        Toast.makeText(this, "$trendingName offer applied", Toast.LENGTH_SHORT).show()
    }


}