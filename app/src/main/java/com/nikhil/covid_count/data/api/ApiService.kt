package com.nikhil.covid_count.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @GET("state_district_wise.json")
    suspend fun getUsers(): ResponseBody
}