package com.nikhil.covid_count.utils

import com.nikhil.covid_count.data.model.BhandaraModel
import org.json.JSONObject

class ParserData {

    companion object {

        fun getData(data: String): BhandaraModel {

            val jsonObject = JSONObject(data)
            val active: String = jsonObject.get("active").toString()
            val confirmed: String = jsonObject.get("confirmed").toString()
            val migratedother: String = jsonObject.get("migratedother").toString()
            val deceased: String = jsonObject.get("deceased").toString()
            val recovered: String = jsonObject.get("recovered").toString()

            val jsonObject2 = jsonObject.getJSONObject("delta")
            val deltaConfirmed = jsonObject2.get("confirmed").toString()
            val deltaDeceased = jsonObject2.get("deceased").toString()
            val deltaRecovered = jsonObject2.get("recovered").toString()

            return BhandaraModel(
                active,
                confirmed,
                migratedother,
                deceased,
                recovered,
                deltaConfirmed,
                deltaDeceased,
                deltaRecovered
            )
        }
    }
}