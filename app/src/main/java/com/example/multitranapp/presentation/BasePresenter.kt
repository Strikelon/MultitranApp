package com.example.multitranapp.presentation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

open class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.addToCompositeDisposable() {
        compositeDisposable.add(this)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun clearRequestsDisposable() {
        compositeDisposable.clear()
    }

}