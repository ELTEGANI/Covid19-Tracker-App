package com.nanotechnology.covid_19statistic.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nanotechnology.covid_19statistic.repositry.StatisticRepository
import com.nanotechnology.covid_19statistic.vo.Resource
import com.nanotechnology.covid_19statistic.vo.Statistic
import javax.inject.Inject


class StatisticViewModel @Inject constructor(statisticRepository: StatisticRepository): ViewModel() {


    val statistic : LiveData<Resource<Statistic>>  = statisticRepository.retrieveAllStatistics()

    val deathsAndUpdated : LiveData<List<Statistic>> = statisticRepository.loadDeathsAndUpdatedTime()

}
