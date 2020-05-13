package com.example.catapp.data.repository

import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.data.repository.CatFactRepository
import com.example.catapp.network.CatFactsApi
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class DefaultCatFactRepository @Inject constructor(
    private val catFactsApi: CatFactsApi
) :
    CatFactRepository {

    override fun getCatFactsIdsAsync(): Deferred<List<CatFactId>> {
        return catFactsApi.getCatFacts()

    }

    override fun getCatFactAsync(id: String): Deferred<CatFact> {
        return catFactsApi.getCatFact(id)
    }

}