package com.example.catapp.data

import com.example.catapp.network.CatFactsApi
import io.reactivex.Single
import javax.inject.Inject

class DefaultCatFactRepository @Inject constructor(private val catFactsApi: CatFactsApi
) :
    CatFactRepository {

    override fun getCatFactsIds(): Single<List<CatFactId>> {
        return catFactsApi.getCatFacts()

    }

    override fun getCatFact(id: String): Single<CatFact> {
        return catFactsApi.getCatFact(id)
            .map { fact -> fact.copy(updatedAt = fact.formatDate()) }
    }

}