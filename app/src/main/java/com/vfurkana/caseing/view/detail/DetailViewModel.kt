package com.vfurkana.caseing.view.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vfurkana.caseing.data.Repository
import com.vfurkana.caseing.data.model.RepositoryAppData
import com.vfurkana.caseing.di.IOScheduler
import com.vfurkana.caseing.utils.StatefulData
import com.vfurkana.caseing.utils.disposeWith
import com.vfurkana.caseing.view.base.BaseViewModel
import io.reactivex.Scheduler

class DetailViewModel @ViewModelInject constructor(private val repository: Repository, @IOScheduler private val ioScheduler: Scheduler) :
    BaseViewModel() {


    private val repositoryData = MutableLiveData<StatefulData<RepositoryAppData>>()
    fun getRepository(): LiveData<StatefulData<RepositoryAppData>> = repositoryData

    fun onBundle(repository: RepositoryAppData?) {
        if (repository != null) repositoryData.value = StatefulData.Success(repository)
        else repositoryData.value = StatefulData.Error()
    }

    fun onFavouriteClick() {
        repositoryData.value?.getSuccessData()?.let { githubRepo ->
            if (githubRepo.isInFavourites) {
                repository.removeFavourite(githubRepo.id)
                    .andThen {
                        repositoryData.postValue(StatefulData.Success(githubRepo.apply { isInFavourites = false }))
                    }
            } else {
                repository.putFavourite(githubRepo.id)
                    .andThen {
                        repositoryData.postValue(StatefulData.Success(githubRepo.apply { isInFavourites = true }))
                    }
            }.subscribeOn(ioScheduler)
                .observeOn(ioScheduler)
                .subscribe().disposeWith(this)
        }
    }
}