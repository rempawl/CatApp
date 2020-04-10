package com.example.catapp.di

import android.content.Context
import com.example.catapp.MainActivity
import com.example.catapp.catFactDetails.CatFactDetailsFragment
import com.example.catapp.catFactDetails.CatFactDetailsViewModel
import com.example.catapp.catFactsIdsList.CatFactsIdsViewModel
import com.example.catapp.catFactsIdsList.CatFactsListFragment
import com.example.catapp.catFactsIdsList.DefaultCatFactsIdsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, NetworkModule::class, AssistedInjectModule::class, ModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }


    val catFactsIdsViewModel: DefaultCatFactsIdsViewModel
    val catFactDetailsViewModelFactory: CatFactDetailsViewModel.Factory

    fun inject(catListFragment: CatFactsListFragment)
    fun inject(mainActivity: MainActivity)
}