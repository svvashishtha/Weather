package com.example.sample.data.models

import com.google.gson.annotations.SerializedName

data class Flags(
    @SerializedName("nearest-station")
    val nearest_station: Double,
    val sources: List<String>,
    val units: String
)