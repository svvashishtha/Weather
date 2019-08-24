package com.example.sample.views.weather

import androidx.lifecycle.ViewModel
import com.example.sample.R
import com.example.sample.data.models.DailyWeather
import com.example.sample.utils.ResourceUtils
import java.text.SimpleDateFormat
import java.util.*

class DailyViewModel(var resourceUtils: ResourceUtils) : ViewModel() {


    val formatterWithDay = SimpleDateFormat("EEE")
    var data: DailyWeather? = null

    fun getIcon(): String? {
        when (data?.icon) {
            "clear-day" -> {
                return resourceUtils.getString(R.string.wi_day_sunny)
            }
            "clear-night" -> {
                return resourceUtils.getString(R.string.wi_night_clear)
            }
            "rain" -> {
                return resourceUtils.getString(R.string.wi_rain)
            }
            "snow" -> {
                return resourceUtils.getString(R.string.wi_snow)
            }
            "sleet" -> {
                return resourceUtils.getString(R.string.wi_sleet)
            }
            "wind" -> {
                return resourceUtils.getString(R.string.wi_windy)
            }
            "fog" -> {
                return resourceUtils.getString(R.string.wi_fog)
            }
            "cloudy" -> {
                return resourceUtils.getString(R.string.wi_cloudy)
            }
            "partly-cloudy-day" -> {
                return resourceUtils.getString(R.string.wi_day_cloudy)
            }
            "partly-cloudy-night" -> {
                return resourceUtils.getString(R.string.wi_night_partly_cloudy)
            }
            "hail" -> {
                return resourceUtils.getString(R.string.wi_hail)
            }
            "thunderstorm" -> {
                return resourceUtils.getString(R.string.wi_thunderstorm)
            }
            "tornado" -> {
                return resourceUtils.getString(R.string.wi_tornado)
            }
            else -> return ""
        }
    }

    fun getRain(): String? {
        data?.precipProbability?.let {
            return String.format("%.0f %%", it * 100)
        }
        return ""
    }

    fun getDay(): String? {
        data?.time?.let {
            if (it.toLong() * 1000 < System.currentTimeMillis())
                return "Today"
            return formatterWithDay.format(Date(it.toLong() * 1000))
        }
        return ""
    }

    fun getTemperature(): String? {
        data?.let {
            return String.format(
                resourceUtils.getString(R.string.temperature),
                it.temperatureLow
            ) +"/"+ String.format(resourceUtils.getString(R.string.temperature), it.temperatureHigh)
        }
        return ""
    }

    fun getApparentTemperature(): String? {
        data?.let {
            return resourceUtils.getString(R.string.apparent_temperature) +"\n"+ String.format(
                resourceUtils.getString(R.string.temperature),
                it.apparentTemperatureLow
            ) +"/"+ String.format(
                resourceUtils.getString(R.string.temperature),
                it.apparentTemperatureHigh
            )
        }
        return ""
    }
}