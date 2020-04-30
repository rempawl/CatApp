package com.example.catapp.di

import com.example.catapp.MainActivity
import com.example.catapp.catFactDetails.DefaultCatFactDetailsViewModel
import com.example.catapp.catFactsIdsList.CatFactsIdsFragment
import com.example.catapp.catFactsIdsList.DefaultCatFactsIdsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, NetworkModule::class, AssistedInjectModule::class, ModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    val catFactsIdsViewModel: DefaultCatFactsIdsViewModel
    val catFactDetailsViewModelFactory: DefaultCatFactDetailsViewModel.Factory

    fun inject(catIdsFragment: CatFactsIdsFragment)
    fun inject(mainActivity: MainActivity)
}