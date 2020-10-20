package com.nanotechnology.covid_19statistic.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Statistic::class], version = 1, exportSchema = false)
abstract class CovidDb : RoomDatabase() {
    abstract fun statisticDao(): StatisticDao
}
