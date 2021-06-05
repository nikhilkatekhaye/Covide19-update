package com.nikhil.covid_count.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nikhil.covid_count.R
import com.nikhil.covid_count.data.api.ApiHelper
import com.nikhil.covid_count.data.api.RetrofitBuilder
import com.nikhil.covid_count.data.model.BhandaraModel
import com.nikhil.covid_count.ui.base.ViewModelFactory
import com.nikhil.covid_count.ui.main.viewmodel.MainViewModel
import com.nikhil.covid_count.utils.ParserData
import com.nikhil.covid_count.utils.Status
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class MainActivity : AppCompatActivity() {

    private lateinit var date: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var activeData: TextView
    private lateinit var confirmedData: TextView
    private lateinit var recoveredData: TextView
    private lateinit var deceasedData: TextView
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUI()
        setupViewModel()
        setupObservers()
        setupDate()
    }

    private fun setupDate() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = getString(R.string.label_covid_report)

        viewModel.getTodayDate().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        date.text = "All Cases Updates " + "\n\n" + "Last updated : " + resource.data.toString()
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun setUpUI() {
        progressBar = findViewById(R.id.progressBar)
        activeData = findViewById(R.id.txt_active_data)
        confirmedData = findViewById(R.id.txt_confirmed_data)
        recoveredData = findViewById(R.id.txt_recovered_data)
        deceasedData = findViewById(R.id.txt_deceased_data)
        pieChart = findViewById(R.id.pie_chart)
        date = findViewById(R.id.txt_date)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { users ->
                            val model = ParserData.getData(users)
                            setUpData(model)
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setUpData(model: BhandaraModel) {
        activeData.text = model.active
        confirmedData.text = model.confirmed
        recoveredData.text = model.recovered
        deceasedData.text = model.deceased


        pieChart.addPieSlice(
                PieModel(
                        "active", model.active.toFloat(),
                        ContextCompat.getColor(this, R.color.orange)))
        pieChart.addPieSlice(
                PieModel(
                        "confirmed", model.confirmed.toFloat(),
                        ContextCompat.getColor(this, R.color.yellow)))
        pieChart.addPieSlice(
                PieModel(
                        "recovered", model.recovered.toFloat(),
                        ContextCompat.getColor(this, R.color.green)))
        pieChart.addPieSlice(
                PieModel(
                        "deceased", model.deceased.toFloat(),
                        ContextCompat.getColor(this, R.color.red)))

        pieChart.startAnimation()
    }
}