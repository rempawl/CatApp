package com.example.catapp.di

import android.net.ConnectivityManager
import com.example.catapp.MyApp.Companion.BASE_URL
import com.example.catapp.network.CatFactsApi
import com.example.catapp.network.NetworkCallback
import com.example.catapp.utils.NetworkConnectionListenerImpl
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
    fun provideNetworkCallback(networkConnectionListener: NetworkCallback.NetworkConnectionListener)
            : ConnectivityManager.NetworkCallback = NetworkCallback(networkConnectionListener)

    @Provides
    @JvmStatic
    @Singleton
    fun provideNetworkConnectionListener(): NetworkCallback.NetworkConnectionListener =
        NetworkConnectionListenerImpl()

    @Provides
    @Reusable
    @JvmStatic
    fun provideCatFactsApi(retrofit: Retrofit): CatFactsApi =
        retrofit.create(CatFactsApi::class.java)

    @Provides
    @Reusable
    @JvmStatic
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Reusable
    @JvmStatic
    fun provideRetrofitInterface(moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}