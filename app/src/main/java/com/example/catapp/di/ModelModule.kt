package com.example.catapp.di

import com.example.catapp.state.DefaultStateModel
import com.example.catapp.state.StateModel
import dagger.Module
import dagger.Provides

@Module
object ModelModule {

    @Provides
    @JvmStatic
    fun provideStateModel(): StateModel =
        DefaultStateModel()
}