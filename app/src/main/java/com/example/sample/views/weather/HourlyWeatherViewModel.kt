package com.example.sample.views.weather

import androidx.lifecycle.ViewModel
import com.example.sample.R
import com.example.sample.data.models.HourlyWeather
import com.example.sample.utils.ResourceUtils
import java.text.SimpleDateFormat
import java.util.*


class HourlyWeatherViewModel(var resourceUtils: ResourceUtils) : ViewModel() {

    var dayEndMillis: Long = 0

    init {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        dayEndMillis = c.timeInMillis
    }

    val formatter = SimpleDateFormat("h:mm a")
    val formatterWithDay = SimpleDateFormat("h:mm a EEE")
    var data: HourlyWeather? = null

    fun getTime(): String? {
        data?.time?.let {

            return if (it.toLong() * 1000 > dayEndMillis)
                formatterWithDay.format(Date(it.toLong() * 1000))
            else
                formatter.format(Date(it.toLong() * 1000))
        }

        return ""
    }

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

    fun getTemperature(): String? {
        data?.temperature?.let {
            return String.format(resourceUtils.getString(R.string.temperature), it)
        }
        return ""
    }

    fun getWindIcon(): Int? {
        data?.windBearing?.let {
            return it - 180
        }
        return 0
    }

}