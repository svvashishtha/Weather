package com.example.sample.network

import com.example.sample.data.models.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherApi {
    @GET("{lat},{lon}")
    fun getWeather(@Path("lat") lat: String, @Path("lon") lon: String, @Query("units") unit: String = "si"): Call<Weather>
}