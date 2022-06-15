package ru.geekbrains.material.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.material.BuildConfig
import ru.geekbrains.material.repository.EarthServerResponseData
import ru.geekbrains.material.repository.PictureOfTheDayResponseData
import ru.geekbrains.material.repository.PictureOfTheDayRetrofitImpl
import ru.geekbrains.material.view.PictureOfTheDayData
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {
    private val sdf = SimpleDateFormat("yyyy-MM-dd")

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

    fun sendRequestYesterday(){
        liveData.postValue(PictureOfTheDayData.Loading(null))
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            PictureOfTheDayData.Error(Throwable("Нет API key!"))
        } else {
            pictureOfTheDayRetrofitImpl.getRetrofit().getPictureOfTheYesterday(apiKey, getDate(-1)).enqueue(callback)
        }
    }

    fun sendRequestTYheDayBeforeYesterday(){
        liveData.postValue(PictureOfTheDayData.Loading(null))
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            PictureOfTheDayData.Error(Throwable("Нет API key!"))
        } else {
            pictureOfTheDayRetrofitImpl.getRetrofit().getPictureOfTheDayBeforeYesterday(apiKey, getDate(-2)).enqueue(callback)
        }
    }

    fun sendRequestTheMars() {
        liveData.postValue(PictureOfTheDayData.Loading(null))
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank())
            PictureOfTheDayData.Error(Throwable("Нет API key!"))
        else
            pictureOfTheDayRetrofitImpl.getRetrofit().getPictureOfTheMars("100", "fhaz",apiKey)
                .enqueue(callback)
    }

    fun sendRequestTheEarthEPIC(){
        liveData.postValue(PictureOfTheDayData.Loading(null))
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank())
            PictureOfTheDayData.Error(Throwable("Нет API key!"))
        else
            pictureOfTheDayRetrofitImpl.getRetrofit().getPictureNaturalEPIC(apiKey)
                .enqueue(callbackEPIC)
    }

    private val callbackEPIC = object  : Callback<List<EarthServerResponseData>>{
        override fun onResponse(
            call: Call<List<EarthServerResponseData>>,
            response: Response<List<EarthServerResponseData>>
        ) {
            if (response.isSuccessful){
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayData.SuccessEarth(it))
                }
            }else {
                if (response.message().isNullOrEmpty()){
                    liveData.postValue(PictureOfTheDayData.Error(Throwable("Неидентифицированное поле!")))
                } else {
                    liveData.postValue(PictureOfTheDayData.Error(Throwable(response.message())))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthServerResponseData>>, t: Throwable) {
            liveData.postValue(PictureOfTheDayData.Error(t))
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

    private fun getDate(int: Int): String{
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, int)
        Log.d("@@@", sdf.format(calendar.time).toString())
        return sdf.format(calendar.time).toString()
    }

}