package com.example.catapp.di

import android.content.Context
import com.example.catapp.catList.CatFactsListFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }

    fun inject(catListFragment: CatFactsListFragment)
}