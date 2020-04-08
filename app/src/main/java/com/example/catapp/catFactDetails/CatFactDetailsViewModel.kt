package com.example.catapp.catFactDetails

import com.example.catapp.catFactsIdsList.CatFactViewModel
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Single

class CatFactDetailsViewModel @AssistedInject constructor(
    catFactRepository: CatFactRepository,
    @Assisted private val factId: String
) : CatFactViewModel<CatFact>(catFactRepository) {

    @AssistedInject.Factory
    interface Factory {
        fun create(factId: String): CatFactDetailsViewModel
    }

    init {
        fetchData()
    }

    override fun getData(): Single<CatFact> {

        return catRepository.getCatFact(factId)
    }
}

