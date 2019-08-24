package com.example.sample.data.models

data class Daily(
    val data: List<DailyWeather>,
    val icon: String,
    val summary: String
)