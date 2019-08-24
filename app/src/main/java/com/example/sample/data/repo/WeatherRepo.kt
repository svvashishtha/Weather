package com.example.sample.data.repo

import androidx.lifecycle.MutableLiveData
import com.example.sample.AppExecutors
import com.example.sample.data.models.Weather
import com.example.sample.network.WeatherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepo @Inject constructor(var weatherApiService: WeatherApi, var appExecutors: AppExecutors) {

    val weather = MutableLiveData<Weather>()
    val error = MutableLiveData<String>()
    fun getWeather(latitude: String, longitude: String) {

        appExecutors.networkIO().execute {
            weatherApiService.getWeather(latitude, longitude).enqueue(object : Callback<Weather> {
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    appExecutors.mainThread().execute {
                        error.value = t.message
                    }
                }

                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    appExecutors.mainThread().execute {
                        weather.value = response.body()
                    }
                }

            })

        }
    }


}