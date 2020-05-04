package com.example.catapp.data

import com.example.catapp.Utils.TEST_CAT_FACT
import com.example.catapp.Utils.TEST_CAT_FACT_MAPPED
import com.example.catapp.Utils.TEST_ID
import com.example.catapp.Utils.TEST_IDS
import com.example.catapp.network.CatFactsApi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import org.junit.Before
import org.junit.Test

class DefaultCatFactRepositoryTest{

    @MockK
    lateinit var catFactsApi : CatFactsApi

    lateinit var repository: DefaultCatFactRepository
    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        repository = DefaultCatFactRepository(catFactsApi)
    }

    @Test
    fun `when catFactsApi returns Single(TEST_CAT_FACT) then getCatFact returns Single(TEST_CAT_FACT_MAPPED)`(){
//        every { catFactsApi.getCatFact(TEST_ID)} returns Deferred(TEST_CAT_FACT)
//
//        val result =  repository.getCatFactRx(TEST_ID)
 //todo
//        result.test()
//            .assertResult(TEST_CAT_FACT_MAPPED)

    }

    @Test
    fun `when catFactsApi returns Single(TEST_IDS) then getFactsIds returns Single(TEST_IDS)`(){
//        every { catFactsApi.getCatFacts() } returns Single.just(TEST_IDS)

//        val result = repository.getCatFactsIdsRx()

//        result
    }

}