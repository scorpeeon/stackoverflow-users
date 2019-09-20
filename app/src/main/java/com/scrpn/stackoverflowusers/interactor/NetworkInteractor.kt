package com.scrpn.stackoverflowusers.interactor

import com.scrpn.stackoverflowusers.StackOverflowUsersApplication
import com.scrpn.stackoverflowusers.domain.model.User
import com.scrpn.stackoverflowusers.network.StackExchangeApi
import com.scrpn.stackoverflowusers.network.model.UsersResponse

import javax.inject.Inject

import io.reactivex.Observable

class NetworkInteractor @Inject constructor(var apiService: StackExchangeApi) {

    init {
        StackOverflowUsersApplication.injector.inject(this)
    }

    fun getUsers(): Observable<List<User>> {
        return apiService.getUsers("20", "desc", "reputation", "stackoverflow")
            .map { userResponse: UsersResponse ->
                if (userResponse.items != null) {
                    userResponse.items.map { user ->
                        User(
                            displayName = user.displayName,
                            userId = user.userId,
                            profileImage = user.profileImage,
                            reputation = user.reputation,
                            location = user.location,
                            creationDate = user.creationDate,
                            following = false,
                            blocked = false
                        )
                    }
                } else { emptyList() }
            }
    }
}
