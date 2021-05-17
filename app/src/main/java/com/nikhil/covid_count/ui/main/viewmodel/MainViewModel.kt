package com.nikhil.covid_count.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nikhil.covid_count.data.repository.MainRepository
import com.nikhil.covid_count.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getBhandaraData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    suspend fun getBhandaraData() : String {

        var response: String = mainRepository.getUsers().byteString().utf8()

        var jsonObject = JSONObject(response)

        var a : String = jsonObject.get("Maharashtra").toString()

        var jsonObject1 = JSONObject(a)

        var b : String = jsonObject1.get("districtData").toString()


        var jsonObject2 = JSONObject(b)

        var c : String = jsonObject2.get("Bhandara").toString()

        return c
    }
}