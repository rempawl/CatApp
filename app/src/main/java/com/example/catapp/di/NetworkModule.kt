package com.example.catapp.di

import com.example.catapp.MyApp.Companion.BASE_URL
import com.example.catapp.network.CatFactsApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
object NetworkModule {

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
//            .addConverterFactory(ScalarsConverterFactory.create())

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}