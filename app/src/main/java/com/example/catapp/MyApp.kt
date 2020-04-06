package com.example.catapp

import android.app.Application

class MyApp : Application() {

    //    val appComponent : AppComponent by lazy {
//
//    }
    companion object {

        const val BASE_URL: String = "https://cat-fact.herokuapp.com"
        const val FACTS_AMOUNT = 30
    }
}