package com.example.catapp.di

import com.example.catapp.DefaultStateModel
import com.example.catapp.StateModel
import dagger.Module
import dagger.Provides

@Module
object ModelModule {
    @Provides
    @JvmStatic
    fun provideErrorModel() : StateModel = DefaultStateModel()
}