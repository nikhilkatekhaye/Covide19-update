package com.nikhil.covid_count.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nikhil.covid_count.data.repository.MainRepository
import com.nikhil.covid_count.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getBhandaraData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    private suspend fun getBhandaraData() : String {

        val response: String = mainRepository.getUsers().byteString().utf8()

        val jsonObject = JSONObject(response)

        val a : String = jsonObject.get("Maharashtra").toString()

        val jsonObject1 = JSONObject(a)

        val b : String = jsonObject1.get("districtData").toString()


        val jsonObject2 = JSONObject(b)

        val c : String = jsonObject2.get("Bhandara").toString()

        return c
    }

    fun getTodayDate() = liveData(Dispatchers.Main) {
        try {
            emit(Resource.success(data = getDate()))
        } catch(exception : Exception){
            emit(Resource.error(data = null, message ="Error Occurred!"))
        }
    }

    private fun getDate() : String {
        val c: Date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(c)
    }
}