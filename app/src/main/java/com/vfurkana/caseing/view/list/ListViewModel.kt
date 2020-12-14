package com.vfurkana.caseing.view.list

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
import javax.inject.Inject

class ListViewModel @ViewModelInject constructor(private val repository: Repository, @IOScheduler private val ioScheduler: Scheduler) : BaseViewModel() {

    private val repositories = MutableLiveData<StatefulData<List<RepositoryAppData>>>()
    fun getRepositories() : LiveData<StatefulData<List<RepositoryAppData>>> = repositories

    fun onUsernameSubmit(username: String) {
        repositories.postValue(StatefulData.Progress())
        repository.getGithubRepositories(username).subscribeOn(ioScheduler)
            .subscribe(
                {
                    repositories.postValue(StatefulData.Success(it))
                }, {
                    repositories.postValue(StatefulData.Error(it.message))
                }).disposeWith(this)
    }

}