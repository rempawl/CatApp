package com.example.catapp.di


import com.example.catapp.DefaultSchedulerProvider
import com.example.catapp.SchedulerProvider
import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.DefaultCatFactRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideCatRepository(catRepository: DefaultCatFactRepository) : CatFactRepository

    @Binds
    fun provideSchedulerProvider(schedulerProvider: DefaultSchedulerProvider) : SchedulerProvider

}