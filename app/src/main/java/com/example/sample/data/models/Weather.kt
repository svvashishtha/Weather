package com.example.sample.data.models

data class Weather(
    val currently: Currently,
    val daily: Daily,
    val flags: Flags,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val minutely: Minutely,
    val offset: Double,
    val timezone: String
)