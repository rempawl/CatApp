package com.example.catapp.data

import com.example.catapp.network.CatFactsApi
import javax.inject.Inject

class DefaultCatRepository @Inject constructor(catFactsApi: CatFactsApi) : CatRepository{
}