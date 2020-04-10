package com.example.catapp

import android.app.Application
import com.example.catapp.di.AppComponent
import com.example.catapp.di.DaggerAppComponent

class MyApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }

    companion object {
        const val BASE_URL: String = "https://cat-fact.herokuapp.com"
        const val FACTS_AMOUNT = 30
    }
}