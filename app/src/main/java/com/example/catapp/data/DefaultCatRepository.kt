package com.example.catapp.data

import com.example.catapp.network.CatFactsApi
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(private val catFactsApi: CatFactsApi
//private val jsonParser: JsonParser
) :
    CatRepository {


    //todo refresh
    //todo persistence

    override fun getCatFactsIds(): Single<List<CatFactId>> {
//        var result : MutableList<CatFactId>? = null
//        catFactsApi.getCatFacts().subscribe(
//            { result = }
//        )
        return catFactsApi.getCatFacts()

    }

}