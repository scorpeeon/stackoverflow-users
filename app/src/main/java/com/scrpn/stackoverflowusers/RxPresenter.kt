package com.scrpn.stackoverflowusers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxPresenter<S> : Presenter<S>() {

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun attachScreen(screen: S) {
        super.attachScreen(screen)
        disposables.dispose()
        disposables = CompositeDisposable()
    }


    override fun detachScreen() {
        disposables.dispose()
        super.detachScreen()
    }

    fun attachDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}
