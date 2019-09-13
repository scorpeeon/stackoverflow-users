package com.scrpn.stackoverflowusers.interactor

import com.scrpn.stackoverflowusers.StackOverflowUsersApplication
import com.scrpn.stackoverflowusers.network.StackExchangeApi
import com.scrpn.stackoverflowusers.network.model.UsersResponse

import javax.inject.Inject

import io.reactivex.Observable

class NetworkInteractor {

    @Inject
    lateinit var apiService: StackExchangeApi

    init {
        StackOverflowUsersApplication.injector.inject(this)
    }

    fun getUsers(): Observable<UsersResponse> {
        return apiService.getUsers("20", "desc", "reputation", "stackoverflow")
    }
}
