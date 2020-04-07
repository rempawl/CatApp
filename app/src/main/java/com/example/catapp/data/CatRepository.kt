package com.example.catapp.data

import io.reactivex.Observable
import io.reactivex.Single

interface CatRepository {
    fun getCatFactsIds(): Single<List<CatFactId>>
}