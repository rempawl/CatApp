package com.example.catapp.data


import kotlinx.coroutines.Deferred

interface CatFactRepository {

    fun getCatFactsIdsAsync(): Deferred<List<CatFactId>>

    fun getCatFactAsync(id: String): Deferred<CatFact>
}