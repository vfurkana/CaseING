package com.vfurkana.caseing.view.base

import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel(), LifecycleObserver {
    val compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposable.dispose()
    }
}