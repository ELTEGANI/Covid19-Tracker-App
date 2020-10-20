package com.nanotechnology.covid_19statistic.di

import android.content.Context
import androidx.room.Room
import com.nanotechnology.covid_19statistic.db.CovidDb
import com.nanotechnology.covid_19statistic.db.StatisticDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context:Context): CovidDb {
        return Room
            .databaseBuilder(context, CovidDb::class.java, "Covid19.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideStatisticDao(db: CovidDb): StatisticDao {
        return db.statisticDao()
    }
}
