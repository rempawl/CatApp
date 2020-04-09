package com.example.catapp.catFactsIdsList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapp.CatFactViewModel
import com.example.catapp.SchedulerProvider
import com.example.catapp.data.CatFactId
import com.example.catapp.data.CatFactRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CatFactsIdsViewModel @Inject constructor(
    catFactRepository: CatFactRepository, schedulerProvider: SchedulerProvider
) : CatFactViewModel<List<CatFactId>>(catFactRepository, schedulerProvider) {


    override fun getData(): Single<List<CatFactId>> {
        return catRepository.getCatFactsIds()
    }


}
