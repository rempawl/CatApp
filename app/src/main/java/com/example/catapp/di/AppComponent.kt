package com.example.catapp.di

import android.content.Context
import com.example.catapp.catFactDetails.CatFactDetailsFragment
import com.example.catapp.catFactDetails.CatFactDetailsViewModel
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModel
import com.example.catapp.catFactsIdsList.CatFactsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, NetworkModule::class,AssistedInjectModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }


    val catFactsIdsViewModel : CatFactsIdsViewModel
    val catFactDetailsViewModelFactory : CatFactDetailsViewModel.Factory

    fun inject(catListFragment: CatFactsListFragment)
}