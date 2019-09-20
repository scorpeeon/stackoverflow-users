package com.scrpn.stackoverflowusers.ui.list

import com.scrpn.stackoverflowusers.RxPresenter
import com.scrpn.stackoverflowusers.interactor.NetworkInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserListPresenter @Inject constructor(var networkInteractor: NetworkInteractor): RxPresenter<UserListScreen>() {

    fun refreshItems() {

        screen?.showLoading(true)
        attachDisposable(
            networkInteractor.getUsers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    screen?.showLoading(false)
                    screen?.onUsersLoaded(result)
                }, { error ->
                    screen?.showLoading(false)
                    screen?.onLoadFailed()
                })
        )
    }
}