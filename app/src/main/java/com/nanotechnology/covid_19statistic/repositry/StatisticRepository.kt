package com.nanotechnology.covid_19statistic.repositry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.nanotechnology.covid_19statistic.AppExecutors
import com.nanotechnology.covid_19statistic.api.ApiResponse
import com.nanotechnology.covid_19statistic.api.ApiSuccessResponse
import com.nanotechnology.covid_19statistic.api.Covid19Service
import com.nanotechnology.covid_19statistic.db.StatisticDao
import com.nanotechnology.covid_19statistic.vo.Resource
import com.nanotechnology.covid_19statistic.vo.Statistic
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val covidService: Covid19Service,
    private val statisticDao: StatisticDao
) {

    fun retrieveAllStatistics(): LiveData<Resource<Statistic>> {
        return object : NetworkBoundResource<Statistic, Statistic>(appExecutors) {
           override fun saveCallResult(item: Statistic) {
              statisticDao.insert(item)
              }

           override fun shouldFetch(data: Statistic?): Boolean = true

           override fun loadFromDb(): LiveData<Statistic> {
              return statisticDao.loadStatistic()
              }

           override fun createCall(): LiveData<ApiResponse<Statistic>> = covidService.getGlobalStatistic()

           override fun processResponse(response: ApiSuccessResponse<Statistic>):
              Statistic {
              return response.body
              }
           }.asLiveData()
        }

    fun loadDeathsAndUpdatedTime(): LiveData<Resource<List<Statistic>>> {
        val data = MediatorLiveData<Resource<List<Statistic>>>()
        data.value = Resource.loading(null)
        data.addSource(statisticDao.loadLastFiveDeaths()) {
          if (it != null) {
             data.value = Resource.success(it)
             }
          }
        return data
        }
}
