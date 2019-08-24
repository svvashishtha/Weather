package com.example.sample.views.weather

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sample.R
import com.example.sample.data.models.DailyWeather
import com.example.sample.data.models.HourlyWeather
import com.example.sample.data.models.Weather
import com.example.sample.data.repo.WeatherRepo
import com.example.sample.utils.ResourceUtils
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class WeatherViewModel @Inject constructor(
    var weatherRepo: WeatherRepo,
    var resourceUtils: ResourceUtils
) :
    ViewModel() {

    var latitude: String = "0.0"
    var longitude: String = "0.0"
    private val weatherObservable: LiveData<Weather> = weatherRepo.weather
    var cityName = MutableLiveData<String>()
    var temperature: LiveData<String> = Transformations.map(weatherObservable) {
        return@map String.format(
            resourceUtils.getString(R.string.temperature),

            it.currently.temperature
        )
    }
    var apparentTemperature: LiveData<String> = Transformations.map(weatherObservable) {
        return@map resourceUtils.getString(R.string.apparent_temperature) + String.format(
            resourceUtils.getString(R.string.temperature), it.currently.apparentTemperature
        )
    }
    var desc: LiveData<String> = Transformations.map(weatherObservable) {
        it.currently.summary
    }

    var iconText: LiveData<String> = Transformations.map(weatherObservable) {
        when (it.currently.icon) {
            "clear-day" -> {
                return@map resourceUtils.getString(R.string.wi_day_sunny)
            }
            "clear-night" -> {
                return@map resourceUtils.getString(R.string.wi_night_clear)
            }
            "rain" -> {
                return@map resourceUtils.getString(R.string.wi_rain)
            }
            "snow" -> {
                return@map resourceUtils.getString(R.string.wi_snow)
            }
            "sleet" -> {
                return@map resourceUtils.getString(R.string.wi_sleet)
            }
            "wind" -> {
                return@map resourceUtils.getString(R.string.wi_windy)
            }
            "fog" -> {
                return@map resourceUtils.getString(R.string.wi_fog)
            }
            "cloudy" -> {
                return@map resourceUtils.getString(R.string.wi_cloudy)
            }
            "partly-cloudy-day" -> {
                return@map resourceUtils.getString(R.string.wi_day_cloudy)
            }
            "partly-cloudy-night" -> {
                return@map resourceUtils.getString(R.string.wi_night_partly_cloudy)
            }
            "hail" -> {
                return@map resourceUtils.getString(R.string.wi_hail)
            }
            "thunderstorm" -> {
                return@map resourceUtils.getString(R.string.wi_thunderstorm)
            }
            "tornado" -> {
                return@map resourceUtils.getString(R.string.wi_tornado)
            }
            else -> return@map ""
        }
    }
    var time: LiveData<String> = Transformations.map(weatherObservable) {
        val formatter = SimpleDateFormat("EEE, MMM d, h:mm a")
        return@map formatter.format(Date(it.currently.time.toLong() * 1000))
    }
    var precipitation: LiveData<String> = Transformations.map(weatherObservable) {
        String.format("%.0f %%", it.currently.precipProbability * 100)
    }
    var uvIndex: LiveData<String> = Transformations.map(weatherObservable) {
        when {
            it.currently.uvIndex <= 2 -> return@map "Low"
            it.currently.uvIndex <= 6 -> return@map "Moderate"
            it.currently.uvIndex <= 8 -> return@map "High"
            it.currently.uvIndex <= 11 -> return@map "Very high"
            else -> return@map "Extreme"
        }
    }
    var hourlyWeather: LiveData<List<HourlyWeather>> = Transformations.map(weatherObservable)
    {
        it.hourly.data
    }

    var dailyWeather :  LiveData<List<DailyWeather>> = Transformations.map(weatherObservable)
    {
        it.daily.data
    }

    fun isLocationvalid(): Boolean {
        return latitude.toDouble() + longitude.toDouble() > 0
    }

    fun getWeather() {
        weatherRepo.getWeather(latitude, longitude)
    }

    fun getLocationName(context: Context) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val listAddresses: List<Address> =
                geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
            if (!listAddresses.isNullOrEmpty()) {

                if (!listAddresses[0].subLocality.isNullOrEmpty())
                    cityName.value = listAddresses[0].subLocality
                else {
                    cityName.value = listAddresses[0].locality
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}