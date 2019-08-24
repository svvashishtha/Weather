package com.example.sample.data.models

data class Hourly(
    val data: List<HourlyWeather>,
    val icon: String,
    val summary: String
)