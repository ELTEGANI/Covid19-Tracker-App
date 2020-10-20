package com.nanotechnology.covid_19statistic.di

import com.nanotechnology.covid_19statistic.api.Covid19Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideCovid19Service(): Covid19Service {
        return Covid19Service.create()
    }
}