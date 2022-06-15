package ru.geekbrains.material.view

import ru.geekbrains.material.repository.EarthServerResponseData
import ru.geekbrains.material.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayData{
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):PictureOfTheDayData()
    data class SuccessEarth(val pictureResponseData: List<EarthServerResponseData>):PictureOfTheDayData()
    data class Error(val error: Throwable): PictureOfTheDayData()
    data class Loading(val progress: Int?): PictureOfTheDayData()
}
