package com.nanotechnology.covid_19statistic.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanotechnology.covid_19statistic.db.Statistic
import com.nanotechnology.covid_19statistic.db.StatisticDao
import com.nanotechnology.covid_19statistic.repositry.StatisticRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class StatisticViewModel @ViewModelInject constructor(var statisticRepository: StatisticRepository) : ViewModel() {


    init {
        viewModelScope.launch {
            getAllCovid19Statistics()
        }
    }

    val statistic: LiveData<Statistic> = statisticRepository.retrieveAllStatistics()

    val deathsAndUpdated: LiveData<List<Statistic>> = statisticRepository.loadDeathsAndUpdatedTime()


    @ExperimentalCoroutinesApi
    fun getAllCovid19Statistics() {
        viewModelScope.launch {
            statisticRepository.getStatistics()
                .onStart { }
                .onCompletion { }
                .catch {
                    if (it is IOException) {
                        Log.d("io", it.toString())
                    }
                }.collect {
                    statisticRepository.insertAllStatistics(it)
            }
        }
    }

}
