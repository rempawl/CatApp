package com.example.catapp.data


import io.reactivex.Single

interface CatFactRepository {
    fun getCatFactsIds(): Single<List<CatFactId>>

    fun getCatFact(id: String) : Single<CatFact>
}