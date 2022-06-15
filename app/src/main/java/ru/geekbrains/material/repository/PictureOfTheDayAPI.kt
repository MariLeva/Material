package ru.geekbrains.material.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheYesterday(@Query("api_key") apiKey: String, @Query("date") data: String): Call<PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDayBeforeYesterday(@Query("api_key") apiKey: String, @Query("date") data: String): Call<PictureOfTheDayResponseData>

    @GET("EPIC/api/natural/images")
    fun getPictureNatural(@Query("api_key") apiKey: String): Call<PictureOfTheDayResponseData>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPictureOfTheMars(@Query("sol") sol: String, @Query("camera") camera: String, @Query("api_key") apiKey: String): Call<PictureOfTheDayResponseData>
}