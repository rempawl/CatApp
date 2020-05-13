package com.example.catapp.testUtils

import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.data.entities.formatUpdateDate
import okhttp3.ResponseBody
import retrofit2.Response

object Utils {
    const val TEST_ID = "123"
    private val RESPONSE_ERROR =
        Response.error<CatFact>(404, ResponseBody.create(null, "404 File not Found"))

    val TEST_IDS = listOf(CatFactId(TEST_ID), CatFactId(TEST_ID))

    val TEST_CAT_FACT = CatFact("test", "09-04-2020")

    val TEST_CAT_FACT_MAPPED = TEST_CAT_FACT.copy(updatedAt = TEST_CAT_FACT.formatUpdateDate())


}