package com.nanotechnology.covid_19statistic.di

import android.content.Context
import com.nanotechnology.covid_19statistic.ui.StatisticFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface AppComponent{
    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(fragment: StatisticFragment)
}


