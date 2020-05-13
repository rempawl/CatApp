package com.example.catapp.di

import android.content.Context
import com.example.catapp.utils.providers.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object UtilsModule {


    @JvmStatic
    @Provides
    fun provideResourcesProvider(context: Context): ResourcesProvider =
        ResourcesProviderImpl(context = context)

}

@Module
interface UtilsModuleInterface{

    @Binds
    fun provideSchedulerProvider(schedulerProvider: DefaultSchedulerProvider): SchedulerProvider

    @Binds
    fun provideDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider) : DispatcherProvider
}