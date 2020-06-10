package com.nanotechnology.covid_19statistic.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanotechnology.covid_19statistic.vo.Statistic

@Dao
interface StatisticDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(statistic: Statistic)

    @Query("SELECT * FROM Statistic ORDER BY cases DESC LIMIT 1")
    fun loadStatistic(): LiveData<Statistic>

    @Query("SELECT * from Statistic ORDER BY deaths DESC LIMIT 4")
    fun loadLastFiveDeaths(): LiveData<List<Statistic>>
}
