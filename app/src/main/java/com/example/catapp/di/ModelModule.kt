package com.example.catapp.di

import com.example.catapp.DefaultErrorModel
import com.example.catapp.ErrorModel
import dagger.Module
import dagger.Provides

@Module
object ModelModule {
    @Provides
    @JvmStatic
    fun provideErrorModel() : ErrorModel{
        return DefaultErrorModel()
    }
}