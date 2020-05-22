package com.example.catapp.testUtils

import com.example.catapp.data.entities.CatFact
import com.example.catapp.data.entities.CatFactId
import com.example.catapp.data.entities.formatUpdateDate
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object Utils {
    const val TEST_ID = "123"
     val RESPONSE_ERROR_404 = HttpException(
        Response.error<CatFact>(404, ResponseBody.create(null, "404 File not Found"))
    )
     val RESPONSE_ERROR_403 = HttpException(
        Response.error<CatFact>(403, ResponseBody.create(null, "404 File not Found"))
    )
     val RESPONSE_ERROR_400 = HttpException(
        Response.error<CatFact>(400, ResponseBody.create(null, "404 File not Found"))
    )
     val RESPONSE_ERROR_500 = HttpException(
        Response.error<CatFact>(500, ResponseBody.create(null, "404 File not Found"))
    )

    val TEST_IDS = listOf(CatFactId(TEST_ID), CatFactId(TEST_ID))

    val TEST_CAT_FACT = CatFact("test", "09-04-2020")

    val TEST_CAT_FACT_MAPPED = TEST_CAT_FACT.copy(updatedAt = TEST_CAT_FACT.formatUpdateDate())

    val ERROR_TEXT = "404"
}