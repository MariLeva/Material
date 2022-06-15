package ru.geekbrains.material.repository

import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseData(
    val date: String,
    val explanation: String,
    val title: String,
    val url: String,
    val photos: ArrayList<MarsServerResponseData>
)

data class MarsServerResponseData(
    @field:SerializedName("img_src")
    val imgSrc: String
)
