package com.nanotechnology.covid_19statistic.repositry

import androidx.lifecycle.LiveData
import com.nanotechnology.covid_19statistic.api.Covid19Service
import com.nanotechnology.covid_19statistic.db.Statistic
import com.nanotechnology.covid_19statistic.db.StatisticDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class StatisticRepository @Inject constructor(private val covidService:Covid19Service,
private var statisticDao : StatisticDao) {

    @ExperimentalCoroutinesApi
    fun getStatistics(): Flow<Statistic> {
        return flow {
            val statisticResponse = covidService.getGlobalStatistic()
            emit(statisticResponse)
        }.flowOn(Dispatchers.IO)
    }

    fun retrieveAllStatistics(): LiveData<Statistic> {
        return statisticDao.loadStatistic()
    }

    fun loadDeathsAndUpdatedTime(): LiveData<List<Statistic>> {
        return statisticDao.loadLastFiveDeaths()
    }

    fun insertAllStatistics(statistic: Statistic){
        statisticDao.insert(statistic)
    }
}
