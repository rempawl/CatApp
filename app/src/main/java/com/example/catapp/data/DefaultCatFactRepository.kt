package com.example.catapp.data

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