package com.nikhil.covid_count.data.model

data class BhandaraModel(
    var active: String,
    var confirmed: String,
    var migrateToOther: String,
    var deceased: String,
    var recovered: String,
    var deltaConfirmed: String,
    var deltaDeceased: String,
    var deltaRecovered: String
)
