package com.example.catapp

import com.example.catapp.catFactDetails.CatFactDetailsViewModelTest
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModelTest
import com.example.catapp.data.CatFact
import com.example.catapp.data.CatFactId
import com.example.catapp.data.formatDate

object Utils {
    val TEST_IDS = listOf<CatFactId>(
        CatFactId(CatFactsIdsViewModelTest.ID), CatFactId(
            CatFactsIdsViewModelTest.ID
        )
    )

    const val TEST_ID = "123"

    val TEST_CAT_FACT = CatFact("test", "09-04-2020")
    val TEST_CAT_FACT_MAPPED = TEST_CAT_FACT.copy(updatedAt = TEST_CAT_FACT.formatDate())



}