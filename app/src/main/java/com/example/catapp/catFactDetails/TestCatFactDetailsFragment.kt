package com.example.catapp.catFactDetails

class TestCatFactDetailsFragment : CatFactDetailsFragment() {

    override fun injectViewModel(): CatFactDetailsViewModel {
        return testViewModel
    }

    companion object{
        lateinit var testViewModel : CatFactDetailsViewModel
    }
}