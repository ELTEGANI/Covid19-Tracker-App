package com.nanotechnology.covid_19statistic.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanotechnology.covid_19statistic.db.Statistic
import com.nanotechnology.covid_19statistic.repositry.StatisticRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okio.IOException

@ExperimentalCoroutinesApi
class StatisticViewModel @ViewModelInject constructor(var statisticRepository: StatisticRepository) : ViewModel() {

    private val _progressBar = MutableLiveData<Boolean>(false)
    val progressBar: LiveData<Boolean>
        get() = _progressBar

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
                .onStart { _progressBar.value = true}
                .onCompletion {_progressBar.value = false }
                .catch {
                    if (it is IOException) {
                        Log.d("io", it.toString())
                    }
                }.collect {covid19Statistics->
                    statisticRepository.insertAllStatistics(covid19Statistics)
            }
        }
    }

}
