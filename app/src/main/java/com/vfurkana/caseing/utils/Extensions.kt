package com.vfurkana.caseing.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vfurkana.caseing.view.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun Disposable.disposeWith(viewModel: BaseViewModel) {
    viewModel.compositeDisposable.add(this)
}