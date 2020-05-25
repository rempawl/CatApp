package com.example.catapp.catFactIdsList

import com.example.catapp.catFactsIdsList.CatFactsIdsFragment
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModel
import com.example.catapp.catFactsIdsList.CatFactsListAdapter

class TestCatFactsIdsIdsFragment : CatFactsIdsFragment() {
    override fun injectViewModel(): CatFactsIdsViewModel = testViewModel


    companion object {
        lateinit var testViewModel: CatFactsIdsViewModel
        lateinit var testAdapter: CatFactsListAdapter
    }

}
