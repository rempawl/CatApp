package com.example.catapp.network

import com.example.catapp.MyApp.Companion.FACTS_AMOUNT
import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.entities.CatFactId
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatFactsApi {
    @GET("/facts/random")
    fun getCatFactsAsync(@Query("amount") amount: Int = FACTS_AMOUNT): Deferred<List<CatFactId>>

    @GET("/facts/{id}")
    fun getCatFactAsync(@Path("id") id: String): Deferred<CatFact>
}