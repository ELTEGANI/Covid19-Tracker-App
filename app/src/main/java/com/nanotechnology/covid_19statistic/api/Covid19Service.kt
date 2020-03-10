package com.nanotechnology.covid_19statistic.api

import androidx.lifecycle.LiveData
import com.nanotechnology.covid_19statistic.vo.Statistic
import retrofit2.http.GET


interface Covid19Service {
    @GET("/all")
    fun getGlobalStatistic():LiveData<Statistic>
}