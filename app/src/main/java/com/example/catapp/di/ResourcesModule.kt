package com.example.catapp.di

import android.content.Context
import com.example.catapp.utils.providers.ResourcesProvider
import com.example.catapp.utils.providers.ResourcesProviderImpl
import dagger.Module
import dagger.Provides

@Module
object ResourcesModule {

    @JvmStatic
    @Provides
    fun provideResourcesProvider(context: Context): ResourcesProvider =
        ResourcesProviderImpl(context = context)

}