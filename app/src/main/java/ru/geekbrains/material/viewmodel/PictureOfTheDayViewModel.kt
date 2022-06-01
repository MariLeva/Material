package ru.geekbrains.material.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.material.BuildConfig
import ru.geekbrains.material.repository.PictureOfTheDayResponseData
import ru.geekbrains.material.repository.PictureOfTheDayRetrofitImpl
import ru.geekbrains.material.view.PictureOfTheDayData

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayData>{
        return liveData
    }

    fun sendRequest(){
        liveData.postValue(PictureOfTheDayData.Loading(null))
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            PictureOfTheDayData.Error(Throwable("Нет API key!"))
        } else {
            pictureOfTheDayRetrofitImpl.getRetrofit().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayData.Success(it))
                }
            }else {
                if (response.message().isNullOrEmpty()){
                    liveData.postValue(PictureOfTheDayData.Error(Throwable("Неидентифицированное поле!")))
                } else {
                    liveData.postValue(PictureOfTheDayData.Error(Throwable(response.message())))
                }
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveData.postValue(PictureOfTheDayData.Error(t))
        }

    }
}