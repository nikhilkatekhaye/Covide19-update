package com.nikhil.covid_count.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nikhil.covid_count.R
import com.nikhil.covid_count.data.api.ApiHelper
import com.nikhil.covid_count.data.api.RetrofitBuilder
import com.nikhil.covid_count.ui.base.ViewModelFactory
import com.nikhil.covid_count.ui.main.viewmodel.MainViewModel
import com.nikhil.covid_count.utils.ParserData
import com.nikhil.covid_count.utils.Status


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var txtData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupObservers()
        setUpUI()
    }

    private fun setUpUI() {
        progressBar = findViewById(R.id.progressBar)
        txtData = findViewById(R.id.txtData)
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
                          var model = ParserData.getData(users)
                            txtData.text = model.active
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
}