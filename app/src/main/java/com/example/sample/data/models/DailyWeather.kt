package com.example.sample.data.models

data class DailyWeather(
    val precipIntensity: Double,
    val precipProbability: Double,
    val time: Int,
    val temperatureMinTime: Int,
    val temperatureMaxTime: Int,
    val windBearing: Int,
    val icon: String,
    val summary: String,
    val temperatureHigh: Double,
    val apparentTemperatureHigh: Double,
    val moonPhase: Double,
    val dewPoint: Double,
    val ozone: Double,
    val precipType: String,
    val windGust: Double,
    val humidity: Double,
    val apparentTemperatureLow: Double,
    val temperatureLow: Double,
    val visibility: Double
)