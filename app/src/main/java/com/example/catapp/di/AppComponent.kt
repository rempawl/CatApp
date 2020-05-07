package com.example.catapp.di

import android.content.Context
import com.example.catapp.MainActivity
import com.example.catapp.catFactDetails.DefaultCatFactDetailsViewModel
import com.example.catapp.catFactsIdsList.CatFactsIdsFragment
import com.example.catapp.catFactsIdsList.DefaultCatFactsIdsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, NetworkModule::class, AssistedInjectModule::class, ResourcesModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    val catFactsIdsViewModel: DefaultCatFactsIdsViewModel
    val catFactDetailsViewModelFactory: DefaultCatFactDetailsViewModel.Factory

    fun inject(catIdsFragment: CatFactsIdsFragment)
    fun inject(mainActivity: MainActivity)
}