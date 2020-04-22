package com.nanotechnology.covid_19statistic.di

import android.content.Context
import androidx.room.Room
import com.nanotechnology.covid_19statistic.api.Covid19Service
import com.nanotechnology.covid_19statistic.db.CovidDb
import com.nanotechnology.covid_19statistic.db.StatisticDao
import com.nanotechnology.covid_19statistic.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
 class ApplicationModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }




    @Provides
    @Singleton
    fun provideCovid19Api(retrofit: Retrofit): Covid19Service {
        return retrofit.create(Covid19Service::class.java)
    }



    @Singleton
    @Provides
    fun  provideDb(context: Context): CovidDb {
        return Room
            .databaseBuilder(context,CovidDb::class.java, "Covid19.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideStatisticDao(db:CovidDb): StatisticDao {
        return db.statisticDao()
    }

}