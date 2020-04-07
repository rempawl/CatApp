package com.example.catapp.di

import android.content.Context
import com.example.catapp.catFactDetails.CatFactDetailsFragment
import com.example.catapp.catFactsIdsList.CatFactsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(catListFragment: CatFactsListFragment)
    fun inject(catFactDetailsFragment: CatFactDetailsFragment)
}