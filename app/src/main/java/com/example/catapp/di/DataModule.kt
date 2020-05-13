package com.example.catapp.di


import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.data.repository.DefaultCatFactRepository
import com.example.catapp.data.errorModel.ErrorModel
import com.example.catapp.data.errorModel.ErrorModelImpl
import com.example.catapp.utils.providers.DefaultSchedulerProvider
import com.example.catapp.utils.providers.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideErrorModel(errorModel: ErrorModelImpl): ErrorModel

    @Binds
    fun provideCatRepository(catRepository: DefaultCatFactRepository): CatFactRepository


}