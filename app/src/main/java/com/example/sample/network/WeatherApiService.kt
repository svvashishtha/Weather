package com.example.sample.network

import com.example.sample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherApiService {
    companion object {
        fun create(): WeatherApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )

                .client(getOkHttpClient())
                .baseUrl("https://api.darksky.net/forecast/" + BuildConfig.DARK_SKY_KEY + "/")
                .build()

            return retrofit.create(WeatherApi::class.java)
        }

        private fun getOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }
    }


}