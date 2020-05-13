package com.example.catapp.data.repository


import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.entities.CatFactId
import kotlinx.coroutines.Deferred

interface CatFactRepository {

    fun getCatFactsIdsAsync(): Deferred<List<CatFactId>>

    fun getCatFactAsync(id: String): Deferred<CatFact>
}