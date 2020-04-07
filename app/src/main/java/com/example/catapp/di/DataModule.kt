package com.example.catapp.di

import com.example.catapp.catFactsIdsList.DefaultJsonParser
import com.example.catapp.catFactsIdsList.JsonParser
import com.example.catapp.data.CatRepository
import com.example.catapp.data.DefaultCatRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun provideCatRepository(catRepository: DefaultCatRepository) : CatRepository

    @Binds
    fun provideJsonParser(jsonParser: DefaultJsonParser) : JsonParser


}