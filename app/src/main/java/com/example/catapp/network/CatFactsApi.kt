package com.example.catapp.network

import com.example.catapp.MyApp.Companion.FACTS_AMOUNT
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactId
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatFactsApi {
    @GET("/facts/random")
    fun getCatFacts(@Query("amount") amount: Int = FACTS_AMOUNT): Single<List<CatFactId>>

    @GET("/facts/{id}")
    fun getCatFact(@Path("id") id: String): Single<CatFact>
}