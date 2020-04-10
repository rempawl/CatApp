package com.example.catapp.di

import com.example.catapp.MyApp.Companion.BASE_URL
import com.example.catapp.network.CatFactsApi
import com.example.catapp.network.NetworkCallback
import com.example.catapp.utils.DefaultNetworkConnectionListener
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideNetworkChangeListener() : NetworkCallback.NetworkConnectionListener{
        return DefaultNetworkConnectionListener()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideCatFactsApi(retrofit: Retrofit): CatFactsApi {
        return retrofit.create(CatFactsApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideMoshi() : Moshi{
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Reusable
    @JvmStatic
    fun provideRetrofitInterface(moshi : Moshi): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}