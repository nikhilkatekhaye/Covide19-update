package com.nikhil.covid_count.data.repository

import com.nikhil.covid_count.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}