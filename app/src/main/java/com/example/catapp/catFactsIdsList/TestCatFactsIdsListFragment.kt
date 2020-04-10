package com.example.catapp.catFactsIdsList

 class TestCatFactsIdsListFragment : CatFactsListFragment() {
    override fun injectViewModel(): CatFactsIdsViewModel = testViewModel

    override fun injectMembers() {
        this.catFactsListAdapter = testAdapter
    }

    companion object {
        lateinit var testViewModel: CatFactsIdsViewModel
        lateinit var testAdapter: CatFactsListAdapter
    }


}