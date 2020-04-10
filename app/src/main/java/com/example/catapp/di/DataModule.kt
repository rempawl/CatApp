package com.example.catapp.di


import com.example.catapp.data.CatFactRepository
import com.example.catapp.data.DefaultCatFactRepository
import com.example.catapp.utils.DefaultSchedulerProvider
import com.example.catapp.utils.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideCatRepository(catRepository: DefaultCatFactRepository) : CatFactRepository

    @Binds
    fun provideSchedulerProvider(schedulerProvider: DefaultSchedulerProvider) : SchedulerProvider

}