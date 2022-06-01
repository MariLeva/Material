package ru.geekbrains.material.repository

import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseData(
    val date: String,
    val exception: String,
    val hdurl: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String
)
