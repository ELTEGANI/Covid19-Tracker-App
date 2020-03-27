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

  @Query("SELECT * FROM Statistic")
  fun loadStatistic(): LiveData<Statistic>
}