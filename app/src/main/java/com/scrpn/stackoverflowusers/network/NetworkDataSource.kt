package com.scrpn.stackoverflowusers.network

import com.scrpn.stackoverflowusers.network.model.User
import com.scrpn.stackoverflowusers.network.model.UsersResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkDataSource @Inject constructor(var apiService: StackExchangeApi) {


    fun getUsers(): Observable<List<User>> {
        return apiService.getUsers("20", "desc", "reputation", "stackoverflow")
            .map { userResponse: UsersResponse ->
                userResponse.items ?: emptyList()
            }
    }
}
